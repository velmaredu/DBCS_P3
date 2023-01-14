package com.uva.bookings.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uva.bookings.model.Booking;
import com.uva.bookings.model.Status;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
    Optional<List<Booking>> findByGuestID(int guestID);
    Optional<List<Booking>> findByGuestIDAndStatus(int guestID, Status status);
    List<Booking> findByDateOutBetweenOrDateInBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByStatusAndFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(Status status, LocalDate starDate, LocalDate endDate);

}

