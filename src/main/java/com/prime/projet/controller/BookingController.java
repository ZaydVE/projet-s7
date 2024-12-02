package com.prime.projet.controller;

import com.prime.projet.repository.entity.Booking;
import com.prime.projet.core.spring.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Créer une réservation
    @PostMapping("/create")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking.getUser().getUserId(), booking.getDestination().getDestinationId(), booking.getNbPassengers());
    }

    // Lister les réservations d'un utilisateur
    @GetMapping("/user/{userId}")
    public Optional<Booking> getUserBookings(@PathVariable Integer userId) {
        return bookingService.getUserBookings(userId);
    }
}