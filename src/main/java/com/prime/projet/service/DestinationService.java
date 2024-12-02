package com.prime.projet.core.spring.service;

import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private static DestinationRepository destinationRepository;
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }
    // Créer une nouvelle destination
    public Destination createDestination(String name, String description, float price, String continent, String country, String city, String type, Date startDate, Date endDate, String lienImage, int nbPlaces) {
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
        destination.setNb_places(nbPlaces);
        return destinationRepository.save(destination);
    }

    // Modifier une destination
    public Destination updateDestination(Integer destinationId, String name, String description, float price, String continent, String country, String city, String type, Date startDate, Date endDate, String lienImage, int nbPlaces) {
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
        destination.setNb_places(nbPlaces);
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