package com.prime.projet.core.spring.controller;

import com.prime.projet.core.data.entity.Booking;
import com.prime.projet.core.spring.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestParam Long userId,
                                                 @RequestParam Long destinationId,
                                                 @RequestParam int nbPassengers) {
        try {
            Booking booking = bookingService.createBooking(userId, destinationId, nbPassengers);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Booking>> getUserBookingHistory(@PathVariable Long userId) {
        try {
            List<Booking> bookingHistory = bookingService.getUserBookingHistory(userId);
            return ResponseEntity.ok(bookingHistory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

