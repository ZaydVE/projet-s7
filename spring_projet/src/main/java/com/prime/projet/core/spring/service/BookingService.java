package com.prime.projet.core.spring.service;

import com.prime.projet.core.data.entity.Booking;
import com.prime.projet.core.data.entity.Destination;
import com.prime.projet.core.data.entity.User;
import com.prime.projet.core.spring.repository.BookingRepository;
import com.prime.projet.core.spring.repository.DestinationRepository;
import com.prime.projet.core.spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          DestinationRepository destinationRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Long userId, Long destinationId, int nbPassengers) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination not found"));

        if (destination.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Destination is no longer available for booking.");
        }

        float totalPrice = (float) (nbPassengers * destination.getPrice());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setDestination(destination);
        booking.setNbPassengers(nbPassengers);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookingHistory(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return bookingRepository.findByUserId(userId);
    }
}

