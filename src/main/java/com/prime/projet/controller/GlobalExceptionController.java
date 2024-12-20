package com.prime.projet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalExceptionController {

    // Redirige vers une page d'erreur générique
    @GetMapping("/error")
    public String showErrorPage(Model model) {
        // Si aucun message d'erreur n'est passé, afficher un message générique
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "Une erreur s'est produite. Veuillez réessayer plus tard.");
        }
        return "error";
    }
}
