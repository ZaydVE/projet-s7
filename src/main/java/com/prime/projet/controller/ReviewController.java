package com.prime.projet.controller;

import com.prime.projet.repository.entity.Review;
import com.prime.projet.core.spring.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Ajouter un avis pour une destination
    @PostMapping("/add")
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review.getUser().getUserId(), review.getDestination().getDestinationId(), review.getRating(), review.getComment());
    }

    // Supprimer un avis
    @DeleteMapping("/delete/{reviewId}")
    public void deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }
}