package com.prime.projet.controller;

import com.prime.projet.repository.entity.Review;
import com.prime.projet.service.ReviewService;
import com.prime.projet.service.dto.ReviewDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //Ouvre une page pour créer une nouvelle review
    @GetMapping("/new")
    public String showReviewForm(Model model) {
        model.addAttribute("reviewDto", new ReviewDto());
        return "review-form";
    }

    //Poster une review
    @PostMapping("/new")
    public String createReview(@ModelAttribute("reviewDto") ReviewDto reviewDto, Model model) {
        Review review = reviewService.createReview(reviewDto);
        model.addAttribute("review", review);
        return "review-confirmation";
    }

    //Affiche toutes les review pour une destination donnée
    @GetMapping("/destination/{destinationId}")
    public String getReviewsForDestination(@PathVariable Integer destinationId, Model model) {
        List<Review> reviews = reviewService.getReviewsForDestination(destinationId);
        model.addAttribute("reviews", reviews);
        return "destination-reviews";
    }
}
