package com.prime.projet.controller;

import ch.qos.logback.core.model.Model;
import com.prime.projet.service.UserService;
import com.prime.projet.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String showRegistrationForm() {
        return "inscription"; // Nom du fichier HTML pour l'inscription (register.html)
    }

    //Créer un utilisateur
    @PostMapping("/new")
    public String registerUser(@ModelAttribute UserDto userDto, Model model) {
        userService.createUser(userDto);
        return "redirect:/users/success";
    }


    //Supprimer un utilisateur
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

}
