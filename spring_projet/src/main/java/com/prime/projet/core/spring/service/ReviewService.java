package com.prime.projet.core.spring.service;

import com.prime.projet.core.data.entity.Review;
import com.prime.projet.core.spring.repository.BookingRepository;
import com.prime.projet.core.spring.repository.DestinationRepository;
import com.prime.projet.core.spring.repository.ReviewRepository;
import com.prime.projet.core.spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookingRepository bookingRepository,
                         UserRepository userRepository,
                         DestinationRepository destinationRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
    }

    public Review addReview(Long userId, Long destinationId, int rating, String comment) {
        boolean hasBooked = bookingRepository.existsByUserIdAndDestinationId(userId, destinationId);
        if (!hasBooked) {
            throw new IllegalArgumentException("User has not booked this destination.");
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        Review review = new Review();
        review.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        review.setDestination(destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination not found")));
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForDestination(Long destinationId) {
        return reviewRepository.findByDestinationId(destinationId);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
