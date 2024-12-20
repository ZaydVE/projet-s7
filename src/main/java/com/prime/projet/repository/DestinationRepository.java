package com.prime.projet.repository;

import com.prime.projet.repository.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    @Query("SELECT d FROM Destination d WHERE " +
            "(:continent IS NULL OR d.continent = :continent) AND " +
            "(:pays IS NULL OR d.country = :pays) AND " +
            "(:startDate IS NULL OR d.startDate >= :startDate) AND " +
            "(:endDate IS NULL OR d.endDate <= :endDate) AND " +
            "(:duration IS NULL OR " +
            "(CASE " +
            "WHEN :duration = 'Escapade courte (4 à 6 jours)' THEN (DATEDIFF(d.endDate, d.startDate) BETWEEN 4 AND 6) " +
            "WHEN :duration = 'Séjour de une semaine (7 à 8 jours)' THEN (DATEDIFF(d.endDate, d.startDate) BETWEEN 7 AND 8) " +
            "WHEN :duration = 'Voyage prolongé (9 à 11 jours)' THEN (DATEDIFF(d.endDate, d.startDate) BETWEEN 9 AND 11) " +
            "WHEN :duration = 'Long séjour (12 à 15 jours)' THEN (DATEDIFF(d.endDate, d.startDate) BETWEEN 12 AND 15) " +
            "ELSE TRUE END)) AND " +
            "(:startDateUser IS NULL OR d.startDate > :startDateUser) AND " +
            "(:endDateUser IS NULL OR d.endDate < :endDateUser) AND " +  // Condition ajoutée pour endDate
            "(:budget IS NULL OR " +
            "(CASE " +
            "WHEN :budget = 'Moins de 1000 €' THEN d.price < 1000 " +
            "WHEN :budget = 'De 1000 € à 3000 €' THEN d.price BETWEEN 1000 AND 3000 " +
            "WHEN :budget = 'De 3000 € à 5000 €' THEN d.price BETWEEN 3000 AND 5000 " +
            "WHEN :budget = 'De 5000 € à 7000 €' THEN d.price BETWEEN 5000 AND 7000 " +
            "WHEN :budget = 'Plus de 7000 €' THEN d.price > 7000 " +
            "ELSE TRUE END))")
    List<Destination> findWithFilters(@Param("continent") String continent,
                                      @Param("pays") String pays,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate,
                                      @Param("personnes") Integer personnes,
                                      @Param("budget") String budget,  // Paramètre pour le budget
                                      @Param("duration") String duration,
                                      @Param("startDateUser") LocalDate startDateUser,
                                      @Param("endDateUser") LocalDate endDateUser);  // Paramètre pour la date de fin de séjour de l'utilisateur



}
