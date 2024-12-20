package com.prime.projet.service;

import com.prime.projet.exception.BookingNotFoundException;
import com.prime.projet.repository.BookingRepository;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
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

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    public Optional<Booking> getBookingById(Integer bookingId) {
        return Optional.ofNullable(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Réservation introuvable pour l'ID " + bookingId)));
    }

    public void updateBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void deleteBooking(Integer bookingId) {
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
