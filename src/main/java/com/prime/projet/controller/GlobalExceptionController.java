package com.prime.projet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    // Redirige vers une page d'erreur générique
    @GetMapping("/error")
    public String showErrorPage(Model model) {
        logger.error("Redirecting to the error page due to an unexpected error.");
        // Si aucun message d'erreur n'est passé, afficher un message générique
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "Une erreur s'est produite. Veuillez réessayer plus tard.");
        }
        return "error";
    }
}
