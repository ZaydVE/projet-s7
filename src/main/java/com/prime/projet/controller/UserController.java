package com.prime.projet.controller;

import com.prime.projet.service.UserService;
import com.prime.projet.service.dto.UserDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Partie utilisateur (inchangée)
    @GetMapping("/new")
    public String showRegistrationForm() {
        return "inscription";
    }

    @PostMapping("/new")
    public String registerUser(@ModelAttribute UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "inscription-success";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Récupérer les informations de l'utilisateur courant
        String username = userDetails.getUsername(); // Nom de l'utilisateur
        String roles = userDetails.getAuthorities().toString(); // Rôles de l'utilisateur

        // Ajouter les informations dans le modèle pour la vue
        model.addAttribute("username", username);
        model.addAttribute("roles", roles);

        // Retourne la page HTML associée
        return "user-profile";
    }

    // -------------------- Partie admin --------------------
    @GetMapping("/newadmin")
    public String showAdminRegistrationForm() {
        return "user-inscription-admin";
    }

    @PostMapping("/newadmin")
    public String registerUserAsAdmin(@ModelAttribute UserDto userDto) {
        userDto.setAdmin(true); // Assurez-vous que l'utilisateur est marqué comme administrateur
        userService.createUser(userDto);
        return "redirect:/users/admin/success";
    }

    @GetMapping("/admin/success")
    public String successPageAdmin() {
        return "inscription-success";
    }

    // -------------------- Partie Modifier un User --------------------
    @GetMapping("/edit-users")
    public String editUserForm() {
        return "user-edit";
    }

    @PostMapping("/edit-users")
    public String updateUser(@ModelAttribute UserDto userDto) {
        userService.updateUser(userDto.getUserId(), userDto);
        return "edit-success";
    }
}