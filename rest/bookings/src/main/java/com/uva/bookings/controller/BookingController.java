package com.uva.bookings.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private static final int NUMHABITACIONES = 10;
    private static final float PRECIOHABITACION = 40;

    public BookingController(BookingRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene el numero de fechas disponibles dentro del rango de fechas
     * especificado.
     * Por defecto se seleccionará desde la fecha actual hasta dentro de un mes.
     * 
     * @param startDate Fecha de inicio.
     * @param endDate   Fecha de fin.
     * 
     * @return Numero de habitaciones disponibles.
     */
    @GetMapping("/availability")
    public int getAvailability(@RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        if (startDate != null && endDate != null) {
            if (validarFechas(startDate, endDate)) {
                throw new BookingException(DEFUSEREXCEP);
            }
        } else {
            startDate = LocalDate.now();
            endDate = LocalDate.now().plusMonths(1);
        }
        List<Booking> bookings = repository.findByDateInOrDateOutBetween(startDate, endDate);

        return NUMHABITACIONES - bookings.size();
    }

    /**
     * Modo 0: Obtiene el listado de reservas realizadas por él, independientemente
     * de su estado con los campos id, price, units, numGuest, status, dateIn,
     * dateOut y created_at. Es recomendable incluir un filtrado por estado.
     * 
     * Modo 1: Obtiene el listado de reservas realizadas en el hotel con los campos
     * id, price, unit, numGuest, status, dateIn, dateOut, created_at y guestName,
     * ordenado por fecha ascendente. Se aconseja un filtrado entre fechas por
     * defecto (Por ejemplo, el intervalo de reservas desde hoy a 15 días atrás y
     * desde hoy a 15 días a futuro. Es recomendable también un filtrado por estado.
     * 
     * 
     * @param mode      Modo
     * @param status    Status
     * @param guestID   ID de invitado
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * 
     * @return
     */
    @GetMapping()
    public List<Booking> getBookings(@RequestParam int mode,
            @RequestParam(value = "status") Status status,
            @RequestParam(value = "guestID", required = false, defaultValue = "-1") int guestID,
            @RequestParam(value = "start_date", required = false) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) LocalDate endDate) {

        if (mode == 0) {
            if (guestID == -1) {
                throw new BookingException(DEFUSEREXCEP);
            }
            return repository.findByGuestIDAndStatus(guestID, status)
                    .orElseThrow(() -> new BookingException(DEFUSEREXCEP));
        } else if (mode == 1) {
            if (startDate == null || endDate == null) {
                startDate = LocalDate.now().minusDays(15);
                endDate = LocalDate.now().plusDays(15);
            }
            return repository
                    .findByStatusAndDateInGreaterThanEqualAndDateOutLessThanEqual(status, startDate, endDate);
        }
        throw new BookingException(DEFUSEREXCEP);
    }

    @GetMapping("/{id}")
    public Booking getBook(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new BookingException(DEFUSEREXCEP));
    }

    // Solo para el usuario de tipo guest
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setBook(@RequestBody Booking body) {
        if (validarFechas(body.getDateIn(), body.getDateOut())) {
            throw new BookingException(DEFUSEREXCEP);
        }
        if (getAvailability(body.getDateIn(), body.getDateOut()) < 1) {
            throw new BookingException(DEFUSEREXCEP);
        }
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

    private boolean validarFechas(LocalDate dateIn, LocalDate dateOut) {
        return dateIn.isBefore(dateOut) && dateIn.isAfter(LocalDate.now());
    }

}
