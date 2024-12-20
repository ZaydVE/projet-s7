package com.prime.projet.service;

import com.prime.projet.exception.ReviewNotFoundException;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.ReviewRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.controller.dto.ReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DestinationRepository destinationRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public ReviewService(ReviewRepository reviewRepository, DestinationRepository destinationRepository) {
        this.reviewRepository = reviewRepository;
        this.destinationRepository = destinationRepository;
    }

    //Créer une review
    public void createReview(ReviewDto reviewDto, com.prime.projet.repository.entity.User user) {
        logger.info("Creating review for destination ID: {} by user ID: {}", reviewDto.getDestinationId(), user.getUserId());
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
        logger.info("Fetching reviews for destination ID: {}", destinationId);
        return reviewRepository.findByDestinationDestinationId(destinationId);
    }

    //Lister les reviews
    public List<Review> getAllReviews() {
        logger.info("Fetching all reviews.");
        return reviewRepository.findAll();
    }

    // Supprimer une review par son ID
    public void deleteReviewById(Integer reviewId) {
        logger.info("Deleting review with ID: {}", reviewId);
        // Vérifie si la review existe avant de la supprimer
        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Review non trouvée avec l'ID : " + reviewId);
        }
        // Supprime la review
        reviewRepository.deleteById(reviewId);
    }

    // Récupérer une review par son ID
    public Review getReviewById(Integer reviewId) {
        logger.info("Fetching review with ID: {}", reviewId);
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Avis avec l'ID " + reviewId + " introuvable."));
    }

    public List<Review> getReviewsByUser(Integer userId) {
        logger.info("Fetching reviews for user ID: {}", userId);
        return reviewRepository.findByUserUserId(userId);
    }

}

