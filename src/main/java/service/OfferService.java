package com.prime.projet.core.spring.service;


import com.prime.projet.core.data.entity.Offer;
import com.prime.projet.core.spring.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    // Créer une offre pour une destination
    public Offer createOffer(Integer destinationId, float percentageDiscount) {
        Offer offer = new Offer();
        offer.setDestination(DestinationService.findById(destinationId));
        offer.setPercentageDiscount(percentageDiscount);
        return offerRepository.save(offer);
    }

    // Supprimer une offre
    public void deleteOffer(Integer offerId) {
        offerRepository.deleteById(offerId);
    }

    // Récupérer les offres liées à une destination
    public Optional<Offer> getOffersByDestination(Integer destinationId) {
        return offerRepository.findById(destinationId);
    }
}