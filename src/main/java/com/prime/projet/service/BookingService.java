package com.prime.projet.service;

import com.prime.projet.controller.UserController;
import com.prime.projet.exception.BookingNotFoundException;
import com.prime.projet.repository.BookingRepository;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    private final DestinationRepository destinationRepository;

    public BookingService(BookingRepository bookingRepository, DestinationRepository destinationRepository, UserService userService, DestinationService destinationService) {
        this.bookingRepository = bookingRepository;
        this.destinationRepository = destinationRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);


    public List<Booking> getAllBookings() {
        logger.info("Fetching all bookings.");
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(Integer userId) {
        logger.info("Fetching bookings for user with ID: {}", userId);
        return bookingRepository.findByUserUserId(userId);
    }

    public Optional<Booking> getBookingById(Integer bookingId) {
        return Optional.ofNullable(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Réservation introuvable pour l'ID " + bookingId)));
    }

    public void updateBooking(Booking booking) {
        logger.info("Updating booking with ID: {}", booking.getBookingId());
        bookingRepository.save(booking);
    }

    public void deleteBooking(Integer bookingId) {
        logger.info("Deleting booking with ID: {}", bookingId);
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isPresent()) {
            // Rendre les places annulées disponibles à nouveau
            Destination destination = booking.get().getDestination();
            destination.setNbPlaces(destination.getNbPlaces() + booking.get().getNbPassengers());
            destinationRepository.save(destination);

            bookingRepository.deleteById(bookingId);
        }
    }
}
