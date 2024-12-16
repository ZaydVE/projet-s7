package com.prime.projet.controller;

import com.prime.projet.repository.ReviewRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.repository.UserRepository;
import com.prime.projet.service.ReviewService;
import com.prime.projet.service.dto.ReviewDto;
import com.prime.projet.service.DestinationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final DestinationService destinationService;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, DestinationService destinationService, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.destinationService = destinationService;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    //Ouvre une page pour créer une nouvelle review
    @GetMapping("/new")
    public String showReviewForm(Model model) {
        // Ajouter la date actuelle au modèle
        LocalDate today = LocalDate.now();
        model.addAttribute("todayDate", today);
        // Ajoute les destinations au modèle
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        //Ajoute un ReviewDto vide dans le modèle
        model.addAttribute("review", new ReviewDto());
        // Récupérer l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer l'utilisateur depuis la base de données
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Ajouter l'utilisateur au modèle
        model.addAttribute("user", user);
        return "review-form"; // Nom du fichier HTML pour l'inscription (register.html)
    }

    // Poster une review
    @PostMapping("/new")
    public String registerReview(@ModelAttribute ReviewDto reviewDto, Model model) {
        // Récupérer l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer l'utilisateur depuis la base de données
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Appelle le service en passant l'utilisateur
        reviewService.createReview(reviewDto, user);

        return "redirect:/reviews/success";
    }

    //Affiche toutes les reviews
    @GetMapping("/liste")
    public String listAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    //Affiche toutes les review pour une destination donnée
    @GetMapping("/{destinationId}")
    public String getReviewsForDestination(@PathVariable Integer destinationId, Model model) {
        // Récupérer les reviews pour la destination
        List<Review> reviews = reviewService.getReviewsForDestination(destinationId);

        // Ajouter les reviews au modèle
        model.addAttribute("reviews", reviews);

        // Récupérer le nom de la destination
        Destination destination = destinationService.getDestinationById(destinationId);
        model.addAttribute("destinationName", destination.getName());

        return "destination-reviews";
    }

    @GetMapping("/success")
    public String successPage() {
        return "review-success";
    }
}
