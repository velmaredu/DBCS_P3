package com.uva.bookings.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uva.bookings.model.Booking;
import com.uva.bookings.model.Status;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<List<Booking>> findByGuestID(int guestID);

    Optional<List<Booking>> findByGuestIDAndStatus(int guestID, Status status);

    @Query("SELECT b FROM Booking b WHERE b.dateIn BETWEEN :startDate AND :endDate OR b.dateOut BETWEEN :startDate AND :endDate")
    List<Booking> findByDateInOrDateOutBetween(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Booking> findByStatusAndDateInGreaterThanEqualAndDateOutLessThanEqual(Status status, LocalDate starDate,
            LocalDate endDate);

}
