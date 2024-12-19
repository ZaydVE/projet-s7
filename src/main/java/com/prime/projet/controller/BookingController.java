package com.prime.projet.controller;

import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.BookingService;
import com.prime.projet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.prime.projet.repository.UserRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    private final com.prime.projet.service.DestinationService destinationService;
    private final UserService userService;

    // Initialisation du formulaire
    @GetMapping("/new/{destinationId}")
    public String initBookingForm(@PathVariable Integer destinationId, Model model) {
        Destination destination = destinationService.getDestinationById(destinationId);
        model.addAttribute("destination", destination);
        model.addAttribute("booking", new Booking());
        return "booking-form"; // Nom du template Thymeleaf
    }

    // Enregistrement de la réservation
    @PostMapping("/new/{destinationId}")
    public String createBooking(@PathVariable Integer destinationId,
                                @RequestParam int nbPassengers, Model model) {
        Destination destination = destinationService.getDestinationById(destinationId);
        User currentUser = userService.getCurrentUser();

        if (destination.getNbPlaces() < nbPassengers) {
            model.addAttribute("error", "Nombre de places insuffisant.");
            return "redirect:/bookings/new/" + destinationId;
        }

        // Création de la réservation
        Booking booking = new Booking();
        booking.setDestination(destination);
        booking.setUser(currentUser);
        booking.setBookingDate(new Date());
        booking.setNbPassengers(nbPassengers);
        booking.setTotalPrice(nbPassengers * destination.getPrice());

        // Mise à jour des places disponibles
        destination.setNbPlaces(destination.getNbPlaces() - nbPassengers);
        destinationService.updateDestinationWithoutImage(
                destination.getDestinationId(),
                destination.getName(),
                destination.getDescription(),
                destination.getPrice(),
                destination.getContinent(),
                destination.getCountry(),
                destination.getCity(),
                destination.getType(),
                destination.getStartDate().toString(),
                destination.getEndDate().toString(),
                destination.getNbPlaces()
        );

        bookingService.updateBooking(booking);

        return "redirect:/destinations"; // Redirection vers une page de confirmation
    }

    public BookingController(com.prime.projet.service.DestinationService destinationService, UserRepository userRepository, UserService userService) {
        this.destinationService = destinationService;
        this.userService = userService;
    }


    @GetMapping("/list")
    public String listAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "booking-list"; // Vue pour afficher toutes les réservations
    }

    @GetMapping("/user")
    public String listUserBookings(Principal principal, Model model) {
        User user = getUserFromPrincipal(principal);
        List<Booking> bookings = bookingService.getBookingsByUser(user.getUserId());
        model.addAttribute("bookings", bookings);
        return "userBookings"; // Vue pour afficher les réservations utilisateur
    }

    @GetMapping("/edit/{id}")
    public String editBookingForm(@PathVariable Integer id, Model model) {
        Booking booking = bookingService.getBookingById(id).orElse(null);
        model.addAttribute("booking", booking);
        return "editBooking"; // Vue pour modifier la réservation
    }

    @PostMapping("/edit/{id}")
    public String editBooking(@PathVariable Integer id,
                              @RequestParam int nbPassengers,
                              @RequestParam float totalPrice) {
        Booking booking = bookingService.getBookingById(id).orElse(null);
        if (booking != null) {
            booking.setNbPassengers(nbPassengers);
            booking.setTotalPrice(totalPrice);
            bookingService.updateBooking(booking);
        }
        return "redirect:/bookings/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings/all";
    }

    private User getUserFromPrincipal(Principal principal) {
        // Simuler la récupération de l'utilisateur à partir du Principal
        User user = new User();
        user.setUserId(1); // Exemple d'ID utilisateur
        return user;
    }
}
