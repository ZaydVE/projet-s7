package com.prime.projet.core.spring.repository;

import com.prime.projet.core.data.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
        List<Destination> findAll();
}