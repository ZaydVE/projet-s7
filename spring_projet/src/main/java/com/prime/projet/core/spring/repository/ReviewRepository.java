package com.prime.projet.core.spring.repository;

import com.prime.projet.core.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}