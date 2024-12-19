package com.prime.projet.controller;

import com.prime.projet.repository.ReviewRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.repository.UserRepository;
import com.prime.projet.service.ReviewService;
import com.prime.projet.controller.dto.ReviewDto;
import com.prime.projet.service.DestinationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final DestinationService destinationService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, DestinationService destinationService, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.destinationService = destinationService;
        this.userRepository = userRepository;
    }

    //Ouvre une page pour créer une nouvelle review
    @GetMapping("/new")
    public String showReviewForm(Model model) {
        addTodayTo(model);
        addDestinationTo(model);
        //Ajoute un ReviewDto vide dans le modèle
        model.addAttribute("review", new ReviewDto());
        // Récupérer l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer l'utilisateur depuis la base de données
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        //envoyer ça dans le service, créer une méthode et l'appeler ici

        // Ajouter l'utilisateur au modèle
        model.addAttribute("user", user);
        return "review-form"; // Nom du fichier HTML pour l'inscription (register.html)
    }

    private void addDestinationTo(Model model) {
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
    }

    private static void addTodayTo(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("todayDate", today);
    }

    // Poster une review
    @PostMapping("/new")
    public String registerReview(@ModelAttribute ReviewDto reviewDto, RedirectAttributes redirectAttributes) {
        // Récupérer l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer l'utilisateur depuis la base de données
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Appelle le service en passant l'utilisateur
        reviewService.createReview(reviewDto, user);

        redirectAttributes.addFlashAttribute("successMessageRegisterReview", "Ajout d'avis réussi !");

        return "redirect:/reviews";
    }

    //Affiche toutes les reviews
    @GetMapping
    public String listAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/review-list-admin")
    public String showAllReviewsForAdmin(Model model) {
        // Récupère toutes les reviews
        List<Review> reviews = reviewService.getAllReviews();

        // Ajoute les reviews au modèle
        model.addAttribute("reviews", reviews);

        // Retourne la vue pour la liste des reviews (admin)
        return "review-list-admin";
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

    //----------------------------------User Supprime une Review ---------------------------------------------

    @GetMapping("/delete/{id}")
    public String showReviewToDelete(@PathVariable Integer id, Model model) {
        // Récupère l'avis par ID
        Review review = reviewService.getReviewById(id);

        // Ajoute les informations de la review au modèle pour l'afficher dans la page
        model.addAttribute("review", review);

        return "review-delete"; // Vue HTML pour afficher les détails avant suppression
    }

    @PostMapping("/confirm-delete/{id}")
    public String confirmDeleteReview(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // Supprime l'avis via le service
        reviewService.deleteReviewById(id);

        // Ajoute un message de confirmation
        redirectAttributes.addFlashAttribute("successMessage", "Avis supprimé avec succès !");

        // Redirige vers la liste des avis
        return "redirect:/reviews/review-list";
    }


    // Gère la suppression de l'avis et redirige vers user-delete.html
    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable Integer id, Model model) {
        // Supprime l'avis via le service
        Review review = reviewService.getReviewById(id);
        reviewService.deleteReviewById(id);

        // Ajoute les informations de l'avis supprimé au modèle (si nécessaire)
        model.addAttribute("deletedReview", review);

        // Redirige vers la page user-delete.html
        return "review-delete";
    }

// Liste des avis de l'utilisateur connecté
    @GetMapping("/review-list")
    public String showAllReviews(Model model) {
        // Récupère l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupère les avis de l'utilisateur
        List<Review> reviews = reviewService.getReviewsByUser(user.getUserId());

        // Ajoute les avis au modèle
        model.addAttribute("reviews", reviews);

        // Retourne la vue pour la liste des avis
        return "review-list";
    }


}

