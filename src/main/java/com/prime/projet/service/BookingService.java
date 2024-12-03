package com.prime.projet.service;

import com.prime.projet.repository.BookingRepository;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.dto.BookingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, DestinationRepository destinationRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
    }

    //Créer une réservation
    public Booking createBooking(BookingDto bookingDto) {
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));
        Destination destination = destinationRepository.findById(bookingDto.getDestinationId())
                .orElseThrow(() -> new IllegalArgumentException("Destination introuvable."));

        Booking booking = new Booking();
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setNbPassengers(bookingDto.getNbPassengers());
        booking.setTotalPrice(destination.getPrice() * bookingDto.getNbPassengers());
        booking.setUser(user);
        booking.setDestination(destination);

        return bookingRepository.save(booking);
    }

    //Lister toutes les réservations
    public List<Booking> getBookingsForUser(Integer userId) {
        return bookingRepository.findByUserUserId(userId);
    }
}
