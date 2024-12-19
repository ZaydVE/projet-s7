package com.prime.projet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class GlobalController {

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
