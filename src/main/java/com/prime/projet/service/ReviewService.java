package com.prime.projet.service;

import com.prime.projet.repository.ReviewRepository;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.service.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //Créer une review
    public Review createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setCreatedAt(reviewDto.getCreatedAt());
        return reviewRepository.save(review);
    }

    //Lister les review pour une destination
    public List<Review> getReviewsForDestination(Integer destinationId) {
        return reviewRepository.findByDestinationDestinationId(destinationId);
    }
}
