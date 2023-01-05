package com.uva.bookings.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uva.bookings.exception.BookingException;
import com.uva.bookings.model.Booking;
import com.uva.bookings.model.Status;
import com.uva.bookings.repository.BookingRepository;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingRepository repository;
    private static final String DEFUSEREXCEP = "Sin resultado";
    // Este es el secreto compartido entre el servidor y el cliente para firmar el
    // token JWT
    private static final String SECRET = "mi_secreto_compartido";

    public BookingController(BookingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/book/availability")
    public List<Availability> getAvailability(@RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        // Query the database for bookings with a dateOut within the given range of
        // dates
        List<Booking> bookings = repository.findByDateOutBetween(startDate, endDate);

        // Create a list of availability objects and add an availability object for
        // each day within the range
        List<Availability> availability = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            boolean isAvailable = !bookings.stream().anyMatch(b -> b.getDateOut().equals(date));
            availability.add(new Availability(date, isAvailable));
        }

        return availability;
    }

    @GetMapping("/book")
    public ResponseEntity<List<Booking>> getBookings(@RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "start_date", required = false) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) LocalDate endDate) {
        String role = getRole();
        String guestID = getGuestID();

        // // Imprimir el rol del usuario en la consola
        // System.out.println("Rol del usuario: " + role);
        if (getRole() == "HOST") {
            // Obtiene el listado de reservas realizadas en el hotel con los campos id,
            // price, unit, numGuest, status, dateIn, dateOut, created_at y guestName,
            // ordenado por fecha ascendente.
            // Se aconseja un filtrado entre fechas por defecto (Por ejemplo, el intervalo
            // de reservas desde hoy a 15 días atrás y desde hoy a 15 días a futuro. Es
            // recomendable también un filtrado por estado.
            List<Booking> bookings = repository.getBookings(status, startDate, endDate);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } else if (getRole() == "GUEST") {
            // Obtiene el listado de reservas realizadas por él, independientemente de su
            // estado con los campos id, price, units, numGuest, status, dateIn, dateOut y
            // created_at.
            // Es recomendable incluir un filtrado por estado.
            List<Booking> bookings = repository.findByGuestIDAndStatus(guestID, status);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/book/{id}")
    public Booking getBook(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new BookingException(DEFUSEREXCEP));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setBook(@RequestBody Booking body) {
        // Validar datos
        repository.save(body);
    }

    // Solo para el usuario host
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateBook(@PathVariable int id, @RequestBody Booking body) {
        try {
            Booking book = repository.findById(id).orElseThrow(() -> new BookingException(DEFUSEREXCEP));
            Booking tmp = new Booking();
            tmp.setDateIn(body.getDateIn());
            tmp.setDateOut(body.getDateOut());
            tmp.setPrice(body.getPrice());
            tmp.setStatus(body.getStatus());
            tmp.setUnits(body.getUnits());
            tmp.setGuestEmail(body.getGuestEmail());
            Booking.copyNonNullProperties(tmp, book);
            book.modified();
            repository.save(book);
            return "Registro actualizado";
        } catch (Exception e) {
            return "Error al actualizar el registro.";
        }
    }

    public static String getRole() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        // Creamos un verificador de token JWT con nuestro secreto compartido
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // Verificamos el token y obtenemos los claims (afirmaciones) del mismo
        DecodedJWT jwt = verifier.verify(token);

        // Obtenemos el valor del claim "rol"
        return jwt.getClaim("rol").asString();

    }

    public static String getGuestID() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        // Creamos un verificador de token JWT con nuestro secreto compartido
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // Verificamos el token y obtenemos los claims (afirmaciones) del mismo
        DecodedJWT jwt = verifier.verify(token);

        // Obtenemos el valor del claim "rol"
        return jwt.getClaim("guestid").asString();

    }

}
