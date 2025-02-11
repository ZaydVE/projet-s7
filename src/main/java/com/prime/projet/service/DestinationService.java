package com.prime.projet.service;

import com.prime.projet.exception.DestinationNotFoundException;
import com.prime.projet.exception.InvalidFilterException;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.controller.dto.DestinationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    private final String UPLOAD_DIR;

    public DestinationService(DestinationRepository destinationRepository,@Value("${file.upload-dir}") String UPLOAD_DIR) {
        this.destinationRepository = destinationRepository;
        this.UPLOAD_DIR = UPLOAD_DIR;
    }

    private static final Logger logger = LoggerFactory.getLogger(DestinationService.class);

    // Lister toutes les destinations
    public List<Destination> getAllDestinations() {
        logger.info("Fetching all destinations.");
        return destinationRepository.findAll();
    }

    // Récupérer les détails d'une destination spécifique
    public Destination getDestinationById(Integer destinationId) {
        logger.info("Fetching destination with ID: {}", destinationId);
        return destinationRepository.findById(destinationId)
                .orElseThrow(() -> new DestinationNotFoundException("Destination avec l'ID " + destinationId + " introuvable."));
    }

    public List<Destination> filterDestinations(String continent, String pays, LocalDate startDate,
                                                LocalDate endDate, Integer personnes, String budget, String duration,
                                                LocalDate startDateUser, LocalDate endDateUser) {
        logger.info("Filtering destinations based on criteria.");
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidFilterException("La date de début ne peut pas être après la date de fin.");
        }
        return destinationRepository.findWithFilters(continent, pays, startDate, endDate, personnes, budget, duration, startDateUser, endDateUser);
    }

    // Récupère les 4 destinations avec la date de départ la plus proche
    public List<Destination> findTop4ClosestDestinations() {
        Date currentDate = new Date(); // Date actuelle
        return destinationRepository.findTop4ByStartDateAfterOrderByStartDateAsc(currentDate);
    }

    // Créer une nouvelle destination avec les paramètres bruts
    public void createDestination(
            String name, String description, float price, String continent,
            String country, String city, String type, String startDate,
            String endDate, int nbPlaces, MultipartFile image) {
        // Créer et mapper le DTO
        DestinationDto destinationDto = createDestinationDto(name, description, price, continent, country, city,
                type, startDate, endDate, nbPlaces);
        // Créer une nouvelle instance de Destination et mapper les données
        Destination destination = mapDtoToDestination(new Destination(), destinationDto);

        saveDestination(destination, image);
    }

    public void updateDestination(Integer destinationId, String name, String description, float price,
                                  String continent, String country, String city, String type,
                                  String startDate, String endDate, int nbPlaces, MultipartFile image) {

        logger.info("Updating destination with ID: {}", destinationId);

        // Récupérer la destination existante par ID
        Destination destination = getDestinationById(destinationId);

        // Créer et mapper le DTO
        DestinationDto destinationDto = createDestinationDto(name, description, price, continent, country, city, type, startDate, endDate, nbPlaces);

        // Mettre à jour les champs
        destination = mapDtoToDestination(destination, destinationDto);

        // Gérer la nouvelle image (supprimer l'ancienne et ajouter la nouvelle si fournie)
        if (image != null && !image.isEmpty()) {
            String newImagePath = saveImage(image); // Sauvegarde de l'image
            deleteImage(destination.getLienImage()); // Supprime l'ancienne image
            destination.setLienImage(newImagePath); // Met à jour le chemin
        }

        // Sauvegarder la destination mise à jour
        destinationRepository.save(destination);
    }

    public void updateDestinationWithoutImage(Integer destinationId, String name, String description, float price,
                                              String continent, String country, String city, String type,
                                              String startDate, String endDate, int nbPlaces) {


        // Récupérer la destination existante
        Destination destination = getDestinationById(destinationId);

        // Mettre à jour les champs sans toucher à l'image
        destination.setName(name);
        destination.setDescription(description);
        destination.setPrice(price);
        destination.setContinent(continent);
        destination.setCountry(country);
        destination.setCity(city);
        destination.setType(type);
        destination.setNbPlaces(nbPlaces);
        destination.setStartDate(java.sql.Date.valueOf(startDate));
        destination.setEndDate(java.sql.Date.valueOf(endDate));

        // Sauvegarder la destination mise à jour sans modifier l'image
        destinationRepository.save(destination);
    }

    // Supprimer une destination et l'image associée
    public void deleteDestination(Integer destinationId) {
        logger.info("Deleting destination with ID: {}", destinationId);
        Destination destination = getDestinationById(destinationId);

        deleteImage(destination.getLienImage());

        destinationRepository.deleteById(destinationId);
    }

    // Méthode privée pour créer un DTO à partir des paramètres bruts
    private DestinationDto createDestinationDto(String name, String description, float price, String continent,
                                                String country, String city, String type,
                                                String startDate, String endDate, int nbPlaces) {
        logger.info("Creating a new destination.");
        DestinationDto dto = new DestinationDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setContinent(continent);
        dto.setCountry(country);
        dto.setCity(city);
        dto.setType(type);
        dto.setNbPlaces(nbPlaces);
        dto.setStartDate(java.sql.Date.valueOf(startDate));
        dto.setEndDate(java.sql.Date.valueOf(endDate));
        return dto;
    }

    // Mapper un DTO vers une entité Destination
    private Destination mapDtoToDestination(Destination destination, DestinationDto dto) {
        destination.setName(dto.getName());
        destination.setDescription(dto.getDescription());
        destination.setPrice(dto.getPrice());
        destination.setContinent(dto.getContinent());
        destination.setCountry(dto.getCountry());
        destination.setCity(dto.getCity());
        destination.setType(dto.getType());
        destination.setNbPlaces(dto.getNbPlaces());
        destination.setStartDate(dto.getStartDate());
        destination.setEndDate(dto.getEndDate());
        return destination;
    }

    // Sauvegarder une destination et gérer l'image
    private void saveDestination(Destination destination, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image); // Sauvegarder la nouvelle image
            destination.setLienImage(imagePath);
        }
        destinationRepository.save(destination); // Enregistrer la destination
    }

    // Sauvegarder une image sur le système de fichiers
    public String saveImage(MultipartFile image) {
        try {
            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new IllegalArgumentException("Le fichier n'a pas de nom valide.");
            }
            String filename = System.currentTimeMillis() + "_" + originalFilename;
            Path path = Paths.get(UPLOAD_DIR).resolve(filename);
            Files.write(path, image.getBytes());
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image.", e);
        }
    }

    // Supprimer une image du système de fichiers
    private void deleteImage(String imagePath) {
        if (imagePath != null) {
            try {
                // Reconstruire le chemin complet du fichier
                Path filePath = Paths.get(UPLOAD_DIR, imagePath.replace("/Evazion/images", ""));
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Erreur lors de la suppression de l'image : " + e.getMessage());
            }
        }
    }
}
