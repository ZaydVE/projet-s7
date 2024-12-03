package com.prime.projet.repository;

import com.prime.projet.repository.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByDestinationDestinationId(Integer destinationId);
}
