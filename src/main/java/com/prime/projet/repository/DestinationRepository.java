package com.prime.projet.repository;

import com.prime.projet.repository.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    List<Destination> findByType(String type);
    @Query("SELECT DISTINCT d.continent FROM Destination d")
    List<String> findDistinctContinents();
}
