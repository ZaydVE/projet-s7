package com.prime.projet.core.spring.service;

import com.prime.projet.repository.entity.Booking;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingService bookingService;

    // Ajouter un avis pour une destination
    public Review addReview(Integer userId, Integer destinationId, int rating, String comment) {
        Optional<Booking> bookings = bookingService.getUserBookings(userId);
        if (bookings.stream().noneMatch(booking -> booking.getDestination().getDestinationId().equals(destinationId))) {
            throw new RuntimeException("L'utilisateur n'a pas réservé cette destination");
        }

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setUser(UserService.findById(userId));
        review.setDestination(DestinationService.findById(destinationId));
        return reviewRepository.save(review);
    }

    // Supprimer un avis
    public void deleteReview(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}