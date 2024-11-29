package com.prime.projet.core.spring.controller;


import com.prime.projet.core.data.entity.Offer;
import com.prime.projet.core.spring.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    // Créer une offre pour une destination
    @PostMapping("/create")
    public Offer createOffer(@RequestBody Offer offer) {
        return offerService.createOffer(offer.getDestination().getDestinationId(), offer.getPercentageDiscount());
    }

    // Supprimer une offre
    @DeleteMapping("/delete/{offerId}")
    public void deleteOffer(@PathVariable Integer offerId) {
        offerService.deleteOffer(offerId);
    }

    // Lister les offres d'une destination
    @GetMapping("/destination/{destinationId}")
    public Optional<Offer> getOffersByDestination(@PathVariable Integer destinationId) {
        return offerService.getOffersByDestination(destinationId);
    }
}