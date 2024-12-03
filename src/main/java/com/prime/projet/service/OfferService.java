package com.prime.projet.service;

import com.prime.projet.repository.OfferRepository;
import com.prime.projet.repository.entity.Offer;
import com.prime.projet.service.dto.OfferDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer createOffer(OfferDto offerDto) {
        Offer offer = new Offer();
        offer.setPercentageDiscount(offerDto.getPercentageDiscount());
        return offerRepository.save(offer);
    }

    public List<Offer> getOffersForDestination(Integer destinationId) {
        return offerRepository.findByDestinationDestinationId(destinationId);
    }
}
