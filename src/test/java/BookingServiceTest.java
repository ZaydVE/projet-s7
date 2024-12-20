package com.prime.projet.service;

import com.prime.projet.exception.BookingNotFoundException;
import com.prime.projet.repository.BookingRepository;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Destination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBookings() {
        // Given
        List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        // When
        List<Booking> result = bookingService.getAllBookings();

        // Then
        assertEquals(bookings.size(), result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingsByUser() {
        // Given
        Integer userId = 1;
        List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        when(bookingRepository.findByUserUserId(userId)).thenReturn(bookings);

        // When
        List<Booking> result = bookingService.getBookingsByUser(userId);

        // Then
        assertEquals(bookings.size(), result.size());
        verify(bookingRepository, times(1)).findByUserUserId(userId);
    }

    @Test
    void testGetBookingById_ExistingBooking() {
        // Given
        Integer bookingId = 1;
        Booking booking = new Booking();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // When
        Optional<Booking> result = bookingService.getBookingById(bookingId);

        // Then
        assertTrue(result.isPresent());
        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    void testGetBookingById_NonExistingBooking() {
        // Given
        Integer bookingId = 1;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(bookingId));
        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    void testUpdateBooking() {
        // Given
        Booking booking = new Booking();

        // When
        bookingService.updateBooking(booking);

        // Then
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testDeleteBooking_ExistingBooking() {
        // Given
        Integer bookingId = 1;
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setNbPassengers(2);
        Destination destination = new Destination();
        destination.setNbPlaces(10);
        booking.setDestination(destination);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // When
        bookingService.deleteBooking(bookingId);

        // Then
        verify(destinationRepository, times(1)).save(destination);
        verify(bookingRepository, times(1)).deleteById(bookingId);
        assertEquals(12, destination.getNbPlaces());
    }

    @Test
    void testDeleteBooking_NonExistingBooking() {
        // Given
        Integer bookingId = 1;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // When
        bookingService.deleteBooking(bookingId);

        // Then
        verify(bookingRepository, never()).deleteById(bookingId);
    }

    @Test
    void testDeleteBooking_WithBookingWithoutDestination() {
        // Given
        Integer bookingId = 1;
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setNbPassengers(2);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // When
        bookingService.deleteBooking(bookingId);

        // Then
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }
}
