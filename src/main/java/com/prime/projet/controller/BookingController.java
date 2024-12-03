package com.prime.projet.controller;


import com.prime.projet.repository.entity.Booking;
import com.prime.projet.service.BookingService;
import com.prime.projet.service.dto.BookingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //Affichage d'une page pour créer une réservation
    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("bookingDto", new BookingDto());
        return "booking-form";
    }

    //Traite la soumission d'une nouvelle réservation
    @PostMapping("/new")
    public String createBooking(@ModelAttribute("bookingDto") BookingDto bookingDto, Model model) {
        Booking booking = bookingService.createBooking(bookingDto);
        model.addAttribute("booking", booking);
        return "booking-confirmation";
    }

    //Affiche toutes les réservations d'un utilisateur donné
    @GetMapping("/user/{userId}")
    public String getUserBookings(@PathVariable Integer userId, Model model) {
        List<Booking> bookings = bookingService.getBookingsForUser(userId);
        model.addAttribute("bookings", bookings);
        return "user-bookings";
    }
}
