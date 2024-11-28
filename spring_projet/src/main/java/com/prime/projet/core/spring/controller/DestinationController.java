package com.prime.projet.core.spring.controller;

import com.prime.projet.core.data.entity.Destination;
import com.prime.projet.core.spring.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Destination>> getDestinationsSorted(@RequestParam String sortBy) {
        try {
            List<Destination> sortedDestinations = destinationService.getDestinationsSorted(sortBy);
            return ResponseEntity.ok(sortedDestinations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        try {
            Destination destination = destinationService.getDestinationById(id);
            return ResponseEntity.ok(destination);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

