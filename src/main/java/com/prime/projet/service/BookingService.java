package com.prime.projet.service;

import com.prime.projet.repository.BookingRepository;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    private DestinationRepository destinationRepository;
    private UserService userService;
    private com.prime.projet.service.DestinationService destinationService;


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    public Optional<Booking> getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
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
