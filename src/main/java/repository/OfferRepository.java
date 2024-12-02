package com.prime.projet.core.spring.repository;

import com.prime.projet.core.data.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

}