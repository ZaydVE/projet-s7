package com.prime.projet.controller;

import com.prime.projet.repository.entity.Destination;
import com.prime.projet.service.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;


    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    //Affiche toutes les destinations
    @GetMapping
    public String showAllDestinations(Model model) {
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        return "destinations";
    }

    //Affiche les détails d'une destination
    @GetMapping("/{id}")
    public String getDestinationDetails(@PathVariable Integer id, Model model) {
        Destination destination = destinationService.getDestinationById(id);
        model.addAttribute("destination", destination);
        return "destination-details";
    }

    //Affiche une page pour créer une nouvelle destination
    @GetMapping("/new")
    public String showCreateDestinationForm() {
        return "destination-form";
    }

    @PostMapping("/new")
    public String createDestination(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") float price,
            @RequestParam("continent") String continent,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("type") String type,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("nbPlaces") int nbPlaces,
            @RequestParam("lienImage") MultipartFile lienImage,
            RedirectAttributes redirectAttributes ) {

        destinationService.createDestination(
                name, description, price, continent, country, city, type, startDate, endDate, nbPlaces, lienImage
        );

        redirectAttributes.addFlashAttribute("successMessageCreateDestination", "Ajout de la destination réussi !");

        return "redirect:/destinations";
    }

    @GetMapping("/edit")
    public String editDestinationForm() {
        return "destination-edit";
    }

    @PostMapping("/edit")
    public String updateDestination(
            @RequestParam("destinationId") Integer destinationId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") float price,
            @RequestParam("continent") String continent,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("type") String type,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("nbPlaces") int nbPlaces,
            @RequestParam(value = "lienImage", required = false) MultipartFile lienImage,
            RedirectAttributes redirectAttributes ) {

        if (lienImage != null && !lienImage.isEmpty()) {
            destinationService.updateDestination(
                    destinationId, name, description, price, continent, country, city, type, startDate, endDate, nbPlaces, lienImage
            );
        } else {
            destinationService.updateDestinationWithoutImage(
                    destinationId, name, description, price, continent, country, city, type, startDate, endDate, nbPlaces
            );
        }

        redirectAttributes.addFlashAttribute("successMessageUpdateDestination", "Modification de la destination réussie !");

        return "redirect:/destinations";
    }

    @GetMapping("/delete")
    public String deleteDestinationForm() {
        return "destination-delete";
    }

    @PostMapping("/delete")
    public String deleteDestination(@RequestParam("destinationId") Integer destinationId, RedirectAttributes redirectAttributes) {
        destinationService.deleteDestination(destinationId);
        redirectAttributes.addFlashAttribute("successMessageDeleteDestination", "Suppression de la destination réussie !");
        return "redirect:/destinations";
    }
}
