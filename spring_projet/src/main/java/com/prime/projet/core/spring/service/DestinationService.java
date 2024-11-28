package com.prime.projet.core.spring.service;

import com.prime.projet.core.data.entity.Destination;
import com.prime.projet.core.spring.repository.DestinationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public List<Destination> getDestinationsSorted(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "price":
                return destinationRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
            case "continent":
                return destinationRepository.findAll(Sort.by(Sort.Direction.ASC, "continent"));
            case "type":
                return destinationRepository.findAll(Sort.by(Sort.Direction.ASC, "type"));
            default:
                throw new IllegalArgumentException("Invalid sort criteria");
        }
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destination not found"));
    }
}

