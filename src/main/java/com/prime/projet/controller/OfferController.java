package com.prime.projet.controller;

import com.prime.projet.repository.entity.Offer;
import com.prime.projet.service.OfferService;
import com.prime.projet.service.dto.OfferDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    //Ouvre une page pour ajouter une nouvelle offre
    @GetMapping("/new")
    public String showOfferForm(Model model) {
        model.addAttribute("offerDto", new OfferDto());
        return "offer-form";
    }

    //Poste une nouvelle offre
    @PostMapping("/new")
    public String createOffer(@ModelAttribute("offerDto") OfferDto offerDto, Model model) {
        Offer offer = offerService.createOffer(offerDto);
        model.addAttribute("offer", offer);
        return "offer-confirmation";
    }

    //Affiche les offres pour une destination donnée
    @GetMapping("/destination/{destinationId}")
    public String getOffersForDestination(@PathVariable Integer destinationId, Model model) {
        List<Offer> offers = offerService.getOffersForDestination(destinationId);
        model.addAttribute("offers", offers);
        return "destination-offers"; // Page Thymeleaf pour afficher les offres d'une destination
    }
}
