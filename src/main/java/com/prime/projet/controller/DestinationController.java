package com.prime.projet.controller;

import com.prime.projet.repository.entity.Destination;
import com.prime.projet.service.DestinationService;
import com.prime.projet.service.dto.DestinationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    //public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/uploads/";


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
    public String showCreateDestinationForm(Model model) {
        model.addAttribute("destinationDto", new DestinationDto());
        return "destination-form";
    }

 /*

    //Ajoute une nouvelle destination
    @PostMapping("/new")
    public String addDestination(
            Model model,
            @ModelAttribute("destinationDto") DestinationDto destinationDto,
            @RequestParam("image") MultipartFile file) throws IOException {

        // Sauvegarde de l'image
        String fileName = destinationService.saveImage(file);
        destinationDto.setLienImage(fileName);

        // Création de la destination
        destinationService.createDestination(destinationDto);

        model.addAttribute("message", "Destination ajoutée avec succès !");
        return "redirect:/destinations"; // Redirection vers la liste des destinations
    }

     */

    //Poste une nouvelle destination
    //@PostMapping("/new")
    //public String createDestination(@ModelAttribute("destinationDto") DestinationDto destinationDto) {
    //    destinationService.createDestination(destinationDto);
    //    return "redirect:/destinations";
    //}

    //Affiche une page pour modifier une destination existante
    @GetMapping("/edit/{id}")
    public String showEditDestinationForm(@PathVariable Integer id, Model model) {
        Destination destination = destinationService.getDestinationById(id);
        model.addAttribute("destination", destination);
        return "destination-edit-form";
    }

    //Poste la modification d'une destination existante
    @PostMapping("/edit/{id}")
    public String editDestination(@PathVariable Integer id, @ModelAttribute("destinationDto") DestinationDto destinationDto) {
        destinationService.updateDestination(id, destinationDto);
        return "redirect:/destinations";
    }

    //Supprimer une destination
    @PostMapping("/delete/{id}")
    public String deleteDestination(@PathVariable Integer id) {
        destinationService.deleteDestination(id);
        return "redirect:/destinations";
    }

    //Applique un filtre ou un tri sur la liste des destinations
    @GetMapping("/filter")
    public String filterDestinations(@RequestParam(required = false) String type, Model model) {
        List<Destination> filteredDestinations = destinationService.filterDestinations(type);
        model.addAttribute("destinations", filteredDestinations);
        return "destinations";
    }
}
