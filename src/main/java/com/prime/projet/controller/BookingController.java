package com.prime.projet.controller;

import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.BookingService;
import com.prime.projet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    public BookingController(com.prime.projet.service.DestinationService destinationService, UserRepository userRepository, UserService userService, UserRepository userRepository1) {
        this.destinationService = destinationService;
        this.userService = userService;
        this.userRepository = userRepository1;
    }

    //Liste de toutes les réservations (admin)
    @GetMapping("/list")
    public String listAllBookings(Model model) {
        logger.info("Listing all bookings.");
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "booking-list"; // Vue pour afficher toutes les réservations
    }

    //Liste les réservations de l'utilisateur connecté
    @GetMapping("/user-list")
    public String showUserBookings(Model model) {
        logger.info("Listing bookings for logged-in user.");
        // Récupère l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        // Récupère les réservations de l'utilisateur
        List<Booking> bookings = bookingService.getBookingsByUser(user.getUserId());
        // Ajoute les réservations au modèle
        model.addAttribute("bookings", bookings);
        // Retourne la vue pour la liste des réservations
        return "booking-list-user";
    }

    // Initialisation du formulaire de réservation
    @GetMapping("/new/{destinationId}")
    public String initBookingForm(@PathVariable Integer destinationId, Model model) {
        logger.info("Initializing booking form for destination ID: {}", destinationId);
        Destination destination = destinationService.getDestinationById(destinationId);
        model.addAttribute("destination", destination);
        model.addAttribute("booking", new Booking());
        return "booking-form"; // Nom du template Thymeleaf
    }

    // Enregistrement de la réservation
    @PostMapping("/new/{destinationId}")
    public String createBooking(@PathVariable Integer destinationId,
                                @RequestParam int nbPassengers, Model model) {
        logger.info("Creating booking for destination ID: {}", destinationId);
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

        return "redirect:/bookings/user-list"; // Redirection vers une page de confirmation
    }

    //Init de la suppression d'une réservation
    @GetMapping("/delete/{id}")
    public String showDeleteBookingPage(@PathVariable Integer id, Model model) {
        logger.info("Loading delete confirmation page for booking ID: {}", id);
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable."));
        model.addAttribute("booking", booking);
        return "booking-delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Integer id) {
        logger.info("Deleting booking with ID: {}", id);

        // Suppression de la réservation
        bookingService.deleteBooking(id);

        // Récupération de l'utilisateur authentifié
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Vérification du rôle de l'utilisateur
        if (principal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            // Redirection pour les administrateurs
            return "redirect:/bookings/list";
        } else {
            // Redirection pour les utilisateurs
            return "redirect:/destinations";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditBookingPage(@PathVariable Integer id, Model model) {
        logger.info("Loading edit page for booking ID: {}", id);
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable."));
        model.addAttribute("booking", booking);
        return "booking-edit";
    }

    @PostMapping("/edit/{id}")
    public String editBooking(@PathVariable Integer id,
                              @RequestParam int nbPassengers,
                              @RequestParam float totalPrice) {
        logger.info("Editing booking with ID: {}", id);
        Booking booking = bookingService.getBookingById(id).orElse(null);
        if (booking != null) {
            booking.setNbPassengers(nbPassengers);
            booking.setTotalPrice(totalPrice);
            bookingService.updateBooking(booking);
        }
        return "redirect:/bookings/list";
    }


}
