package com.prime.projet.controller;

import com.prime.projet.service.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class GlobalController {

    private final DestinationService destinationService;

    public GlobalController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Récupère les 4 destinations avec les dates de départ les plus proches
        model.addAttribute("closestDestinations", destinationService.findTop4ClosestDestinations());
        return "index"; // Affiche le fichier "index.html"
    }

    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage(){
        return "about-us";
    }

    @GetMapping("/faq")
    public String showFaqPage(){
        return "faq";
    }

    @GetMapping("/cgv")
    public String showCgvPage(){
        return "cgv";
    }

    @GetMapping("/politique-de-confidentialite")
    public String showPoliticPage(){
        return "politique-de-confidentialite";
    }


}
