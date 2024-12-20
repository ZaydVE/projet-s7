package com.prime.projet.controller;

import com.prime.projet.service.DestinationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class GlobalController {

    private final DestinationService destinationService;
    private static final Logger logger = LoggerFactory.getLogger(GlobalController.class);

    public GlobalController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        logger.info("Loading home page with top 4 closest destinations.");
        // Récupère les 4 destinations avec les dates de départ les plus proches
        model.addAttribute("closestDestinations", destinationService.findTop4ClosestDestinations());
        return "index"; // Affiche le fichier "index.html"
    }

    @GetMapping("/contact")
    public String showContactPage(){
        logger.info("Loading contact page.");
        return "contact";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage(){
        logger.info("Loading About Us page.");
        return "about-us";
    }

    @GetMapping("/faq")
    public String showFaqPage(){
        logger.info("Loading FAQ page.");
        return "faq";
    }

    @GetMapping("/cgv")
    public String showCgvPage(){
        logger.info("Loading CGV page.");
        return "cgv";
    }

    @GetMapping("/politique-de-confidentialite")
    public String showPoliticPage(){
        logger.info("Loading privacy policy page.");
        return "politique-de-confidentialite";
    }


}
