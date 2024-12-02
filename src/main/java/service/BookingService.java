package com.prime.projet.core.spring.service;

import com.prime.projet.core.data.entity.Booking;
import com.prime.projet.core.data.entity.Destination;
import com.prime.projet.core.data.entity.User;
import com.prime.projet.core.spring.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private UserService userService;

    // Créer une réservation
    public Booking createBooking(Integer userId, Integer destinationId, Integer nbPassengers) {
        User user = UserService.findById(userId);
        Destination destination = DestinationService.findById(destinationId);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setDestination(destination);
        booking.setNbPassengers(nbPassengers);
        booking.setTotalPrice(destination.getPrice() * nbPassengers); // Exemple de calcul du prix
        return bookingRepository.save(booking);
    }

    // Obtenir les réservations d'un utilisateur
    public Optional<Booking> getUserBookings(Integer userId) {
        return bookingRepository.findById(userId);
    }
}