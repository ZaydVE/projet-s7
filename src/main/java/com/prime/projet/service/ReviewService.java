package com.prime.projet.service;

import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.ReviewRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.controller.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DestinationRepository destinationRepository;

    public ReviewService(ReviewRepository reviewRepository, DestinationRepository destinationRepository) {
        this.reviewRepository = reviewRepository;
        this.destinationRepository = destinationRepository;
    }

    //Créer une review
    public void createReview(ReviewDto reviewDto, com.prime.projet.repository.entity.User user) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());

        // Récupère la destination depuis l'ID
        Destination destination = destinationRepository.findById(reviewDto.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination non trouvée"));
        review.setDestination(destination);

        // Associe l'utilisateur connecté
        review.setUser(user);

        // Sauvegarde la review
        reviewRepository.save(review);
    }

    //Lister les review pour une destination
    public List<Review> getReviewsForDestination(Integer destinationId) {
        return reviewRepository.findByDestinationDestinationId(destinationId);
    }

    //Lister les reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
