package com.prime.projet.controller;

import com.prime.projet.repository.entity.Destination;
import com.prime.projet.core.spring.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    // Créer une destination (formulaire et vue HTML)
    @GetMapping("/create")
    public String showCreateForm() {
        return "create_destination"; // page HTML où l'utilisateur peut créer une destination
    }

    @PostMapping("/create")
    public String createDestination(@ModelAttribute Destination destination) {
        destinationService.createDestination(destination.getName(), destination.getDescription(), destination.getPrice(),
                destination.getContinent(), destination.getCountry(), destination.getCity(), destination.getType(),
                destination.getStartDate(), destination.getEndDate(), destination.getLienImage(), destination.getNb_places());
        return "redirect:/destinations/"; // Redirige vers la liste après la création
    }

    // Modifier une destination (formulaire et vue HTML)
    @GetMapping("/update/{destinationId}")
    public String showUpdateForm(@PathVariable Integer destinationId, Model model) {
        Destination destination = destinationService.findById(destinationId);
        model.addAttribute("destination", destination);
        return "update_destination"; // page HTML pour modifier la destination
    }

    @PostMapping("/update/{destinationId}")
    public String updateDestination(@PathVariable Integer destinationId, @ModelAttribute Destination destination) {
        destinationService.updateDestination(destinationId, destination.getName(), destination.getDescription(), destination.getPrice(),
                destination.getContinent(), destination.getCountry(), destination.getCity(), destination.getType(),
                destination.getStartDate(), destination.getEndDate(), destination.getLienImage(), destination.getNb_places());
        return "redirect:/destinations/"; // Redirige vers la liste après la mise à jour
    }

    // Supprimer une destination
    @GetMapping("/delete/{destinationId}")
    public String deleteDestination(@PathVariable Integer destinationId) {
        destinationService.deleteDestination(destinationId);
        return "redirect:/destinations/"; // Redirige vers la liste après suppression
    }

    // Lister toutes les destinations
    @GetMapping()
    public String listDestinations(Model model) {
        List<Destination> destinations = destinationService.listDestinations();
        model.addAttribute("destinations", destinations);
        return "list_destinations"; // Page HTML où les destinations sont listées
    }

    // Trouver une destination par ID (détail d'une destination)
    @GetMapping("/{destinationId}")
    public String findDestinationById(@PathVariable Integer destinationId, Model model) {
        Destination destination = destinationService.findById(destinationId);
        model.addAttribute("destination", destination);
        return "destination_detail"; // Page HTML qui affiche le détail de la destination
    }
}
