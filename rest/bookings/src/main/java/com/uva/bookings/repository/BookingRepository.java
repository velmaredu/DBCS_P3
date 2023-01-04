package com.uva.bookings.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uva.bookings.model.Booking;
import com.uva.bookings.model.Status;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
    List<Booking> findByDateOutBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByGuestID(int guestID);
    List<Booking> findByGuestIDAndStatus(int guestID, Status status);
}

