package com.prime.projet.core.spring.service;

import com.prime.projet.core.data.entity.Destination;
import com.prime.projet.core.spring.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private static DestinationRepository destinationRepository;

    // Créer une nouvelle destination
    public Destination createDestination(String name, String description, float price, String continent, String country, String city, String type, java.util.Date startDate, java.util.Date endDate, String lienImage) {
        Destination destination = new Destination();
        destination.setName(name);
        destination.setDescription(description);
        destination.setPrice(price);
        destination.setContinent(continent);
        destination.setCountry(country);
        destination.setCity(city);
        destination.setType(type);
        destination.setStartDate(startDate);
        destination.setEndDate(endDate);
        destination.setLienImage(lienImage);
        return destinationRepository.save(destination);
    }

    // Modifier une destination
    public Destination updateDestination(Integer destinationId, String name, String description, float price, String continent, String country, String city, String type, java.util.Date startDate, java.util.Date endDate, String lienImage) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new RuntimeException("Destination non trouvée"));
        destination.setName(name);
        destination.setDescription(description);
        destination.setPrice(price);
        destination.setContinent(continent);
        destination.setCountry(country);
        destination.setCity(city);
        destination.setType(type);
        destination.setStartDate(startDate);
        destination.setEndDate(endDate);
        destination.setLienImage(lienImage);
        return destinationRepository.save(destination);
    }

    // Supprimer une destination
    public void deleteDestination(Integer destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElseThrow(() -> new RuntimeException("Destination non trouvée"));
        destinationRepository.delete(destination);
    }

    // Lister toutes les destinations
    public List<Destination> listDestinations() {
        return destinationRepository.findAll();
    }

    // Trouver une destination par son ID
    public static Destination findById(Integer destinationId) {
        return destinationRepository.findById(destinationId).orElseThrow(() -> new RuntimeException("Destination non trouvée"));
    }
}