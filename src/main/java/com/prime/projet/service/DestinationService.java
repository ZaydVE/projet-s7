package com.prime.projet.service;

import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.service.dto.DestinationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    // Répertoire où les images seront stockées
    private static final String UPLOAD_DIR = "src/main/resources/static/assets/";

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    //Lister toutes les destinations
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    //Récupérer les détails d'une destination spécifique
    public Destination getDestinationById(Integer destinationId) {
        return destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination introuvable."));
    }

    //Créer une nouvelle destination
    public void createDestination(DestinationDto destinationDto) {
        // Conversion du DTO en entité
        Destination destination = new Destination();
        destination = setDestination(destination, destinationDto);

        // Sauvegarde dans la base de données
        destinationRepository.save(destination);
    }

    //Créer une nouvelle destination
    //public void createDestination(DestinationDto destinationDto) {
    //    Destination destination = new Destination();
    //    destination = setDestination(destination, destinationDto);
    //    destinationRepository.save(destination);
    //}

    public void updateDestination(Integer destinationId, DestinationDto destinationDto) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination introuvable."));
        destination = setDestination(destination, destinationDto);
        destinationRepository.save(destination);
    }

    //Supprimer une destination
    public void deleteDestination(Integer userId) {
        destinationRepository.deleteById(userId);
    }

    //Filtrer les destinations
    public List<Destination> filterDestinations(String type) {
        if (type != null && !type.isEmpty()) {
            return destinationRepository.findByType(type);
        }
        return destinationRepository.findAll();
    }

    //Méthode pour pouvoir utiliser update et create
    public Destination setDestination(Destination destination, DestinationDto destinationDto) {
        destination.setName(destinationDto.getName());
        destination.setDescription(destinationDto.getDescription());
        destination.setPrice(destinationDto.getPrice());
        destination.setContinent(destinationDto.getContinent());
        destination.setCountry(destinationDto.getCountry());
        destination.setCity(destinationDto.getCity());
        destination.setType(destinationDto.getType());
        destination.setLienImage(destinationDto.getLienImage());
        destination.setNbPlaces(destinationDto.getNbPlaces());
        return destination;
    }

}