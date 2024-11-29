package com.prime.projet.core.spring.controller;


import com.prime.projet.core.data.entity.Destination;
import com.prime.projet.core.spring.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    // Créer une destination
    @PostMapping("/create")
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.createDestination(destination.getName(), destination.getDescription(), destination.getPrice(),
                destination.getContinent(), destination.getCountry(), destination.getCity(), destination.getType(),
                destination.getStartDate(), destination.getEndDate(), destination.getLienImage());
    }

    // Modifier une destination
    @PutMapping("/update/{destinationId}")
    public Destination updateDestination(@PathVariable Integer destinationId, @RequestBody Destination destination) {
        return destinationService.updateDestination(destinationId, destination.getName(), destination.getDescription(), destination.getPrice(),
                destination.getContinent(), destination.getCountry(), destination.getCity(), destination.getType(),
                destination.getStartDate(), destination.getEndDate(), destination.getLienImage());
    }

    // Supprimer une destination
    @DeleteMapping("/delete/{destinationId}")
    public void deleteDestination(@PathVariable Integer destinationId) {
        destinationService.deleteDestination(destinationId);
    }

    // Lister toutes les destinations
    @GetMapping("/")
    public List<Destination> listDestinations() {
        return destinationService.listDestinations();
    }

    // Trouver une destination par ID
    @GetMapping("/{destinationId}")
    public Destination findDestinationById(@PathVariable Integer destinationId) {
        return DestinationService.findById(destinationId);
    }
}