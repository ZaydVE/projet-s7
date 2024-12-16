package com.prime.projet.service;

import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.service.dto.DestinationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    // Répertoire où les images seront stockées
    private static final String UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "projet-s7", "src", "main", "resources", "static", "assets").toString();

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

    // Créer une nouvelle destination avec les paramètres bruts
    public void createDestination(
            String name, String description, float price, String continent,
            String country, String city, String type, String startDate,
            String endDate, int nbPlaces, MultipartFile image) {

        // Créer et mapper le DTO
        DestinationDto destinationDto = new DestinationDto();
        destinationDto.setName(name);
        destinationDto.setDescription(description);
        destinationDto.setPrice(price);
        destinationDto.setContinent(continent);
        destinationDto.setCountry(country);
        destinationDto.setCity(city);
        destinationDto.setType(type);
        destinationDto.setNbPlaces(nbPlaces);
        destinationDto.setStartDate(java.sql.Date.valueOf(startDate));
        destinationDto.setEndDate(java.sql.Date.valueOf(endDate));

        // Appel à la méthode interne pour sauvegarder
        saveDestination(destinationDto, image);
    }

    // Sauvegarder une destination en gérant le fichier image
    private void saveDestination(DestinationDto dto, MultipartFile image) {
        Destination destination = mapDtoToDestination(new Destination(), dto);

        // Sauvegarder l'image
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            destination.setLienImage(imagePath);
        }

        // Sauvegarder la destination dans la base de données
        destinationRepository.save(destination);
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



    /*
    //Créer une nouvelle destination
    public void createDestination(DestinationDto destinationDto) {
        // Conversion du DTO en entité
        Destination destination = new Destination();
        destination = setDestination(destination, destinationDto);

        // Sauvegarde dans la base de données
        destinationRepository.save(destination);
    }
     */

    // Mettre à jour une destination existante
    public void updateDestination(Integer destinationId, DestinationDto destinationDto, MultipartFile image) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination introuvable."));

        destination = mapDtoToDestination(destination, destinationDto);

        // Sauvegarder la nouvelle image, si fournie
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            destination.setLienImage(imagePath);
        }

        destinationRepository.save(destination);
    }

    /*
    public void updateDestination(Integer destinationId, DestinationDto destinationDto) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination introuvable."));
        destination = setDestination(destination, destinationDto);
        destinationRepository.save(destination);
    }
     */

    // Supprimer une destination et l'image associée
    public void deleteDestination(Integer destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination introuvable."));

    public List<String> getAllContinents() {
        List<String> continents = destinationRepository.findDistinctContinents();
        System.out.println("Continents dans DestinationService : " + continents); // Ajoute un débogage ici
        return continents;
    }

        // Supprimer l'image associée si elle existe
        deleteImage(destination.getLienImage());

        destinationRepository.delete(destination);
    }

    /*
    //Supprimer une destination
    public void deleteDestination(Integer userId) {
        destinationRepository.deleteById(userId);
    }
    */

    //Filtrer les destinations
    public List<Destination> filterDestinations(String type) {
        if (type != null && !type.isEmpty()) {
            return destinationRepository.findByType(type);
        }
        return destinationRepository.findAll();
    }


    /*
    //Méthode pour pouvoir utiliser update et create
    public Destination setDestination(Destination destination, DestinationDto destinationDto) {
        destination.setName(destinationDto.getName());
        destination.setDescription(destinationDto.getDescription());
        destination.setPrice(destinationDto.getPrice());
        destination.setContinent(destinationDto.getContinent());
        destination.setCountry(destinationDto.getCountry());
        destination.setCity(destinationDto.getCity());
        destination.setType(destinationDto.getType());
        destination.setStartDate(destinationDto.getStartDate());
        destination.setEndDate(destinationDto.getEndDate());
        destination.setLienImage(destinationDto.getLienImage());
        destination.setNbPlaces(destinationDto.getNbPlaces());
        return destination;
    }
    */

    // Sauvegarder une image sur le système de fichiers
    private String saveImage(MultipartFile image) {
        try {
            String filename = image.getOriginalFilename();
            if (filename == null || filename.isBlank()) {
                throw new IllegalArgumentException("Le fichier n'a pas de nom valide.");
            }
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
                Path filePath = Paths.get(UPLOAD_DIR, imagePath.replace("/assets/", ""));
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Erreur lors de la suppression de l'image : " + e.getMessage());
            }
        }
    }


}