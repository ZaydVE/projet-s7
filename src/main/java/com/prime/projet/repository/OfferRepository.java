package com.prime.projet.repository;

import com.prime.projet.repository.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findByDestinationDestinationId(Integer destinationId);
}