package com.prime.projet.core.spring.controller;

import com.prime.projet.core.data.entity.Review;
import com.prime.projet.core.spring.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestParam Long userId,
                                            @RequestParam Long destinationId,
                                            @RequestParam int rating,
                                            @RequestParam String comment) {
        try {
            Review review = reviewService.addReview(userId, destinationId, rating, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<Review>> getReviewsForDestination(@PathVariable Long destinationId) {
        List<Review> reviews = reviewService.getReviewsForDestination(destinationId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}

