package com.prime.projet.controller;

import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.UserService;
import com.prime.projet.controller.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // ---------------INSCRIPTION-------------------------
    // Partie utilisateur (inchangée)

    //Bouton pour montrer la page de profil
        @GetMapping("/user-profile")
        public String showUserProfile (Model model) {
            // Récupérer l'utilisateur connecté
            User user = getCurrentUser();

            // Convertir l'utilisateur en UserDto pour pré-remplir le formulaire
            UserDto userDto = userService.createDto(user);

            // Ajouter le UserDto au modèle
            model.addAttribute("user", userDto);
            return "user-profile"; // Fichier user-profile.html dans templates
        }

        private User getCurrentUser() {
            org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            }

    //Liste des utilisateurs
        @GetMapping("/liste")
        public String showAllUsers (Model model){
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "user-list";
        }

        @GetMapping("/new")
        public String showRegistrationForm () {
            return "inscription";
        }

        @PostMapping("/new")
        public String registerUser (@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes){
            userService.createUser(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "Inscription réussie!");
            return "redirect:/";
        }



        // -------------------- ADMIN CREE UN UTILISATEUR OU UN ADMIN --------------------
        @GetMapping("/newadmin")
        public String showAdminRegistrationForm () {
            return "user-inscription-admin";
        }

        @PostMapping("/newadmin")
        public String registerUserAsAdmin (@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes){
            userDto.setAdmin(true); // Assurez-vous que l'utilisateur est marqué comme administrateur
            userService.createUser(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "Création réussie!");
            return "redirect:/users/liste";
        }


        // -------------------- Partie User Modifie ses Informations --------------------

        @GetMapping("/user-edit-himself")
        public String editUserForm(Model model) {
            // Récupérer l'utilisateur connecté
            User user = getCurrentUser();

            // Convertir l'utilisateur en UserDto pour pré-remplir le formulaire
            UserDto userDto = userService.createDto(user);

            // Ajouter le UserDto au modèle
            model.addAttribute("user", userDto);

            return "user-edit-himself";
        }

        @PostMapping("/user-edit-himself")
        public String updateUserHimself (@ModelAttribute UserDto userDto, @AuthenticationPrincipal UserDetails
        currentUser){
            // Récupérer l'utilisateur connecté grâce à son email (ou username)
            Optional<User> user = userRepository.findByEmail(currentUser.getUsername());
            if (user != null) {
                userDto.setUserId(user.get().getUserId()); // Assigne l'ID utilisateur
                userService.updateUser(user.get().getUserId(), userDto); // Appelle le service
            }
            return "redirect:/users/user-edit-himself-success";
        }

        @GetMapping("/user-edit-himself-success")
        public String userEditHimselfSuccessPage () {
            return "user-edit-himself-success";
        }


        // -------------------- Partie Admin ouvre le centre de contrôle admin ----------------------------
        //Ouvrir la page admin
        @GetMapping("/admin")
        public String showAdminPage () {
        return "admin";
        }

        // -------------------- Partie Modifier un User --------------------
        @GetMapping("/user-edit/{id}")
        public String editUserForm(@PathVariable Integer id, Model model) {
            User user = userService.findById(id);
            model.addAttribute("user", user);
            return "user-edit";
        }

        @PostMapping("/user-edit/{id}")
        public String updateUser (@PathVariable Integer id, @ModelAttribute UserDto userDto){
            userService.updateUser(id, userDto);
            return "redirect:/users/liste";
        }

        //-------------------- Partie Admin Supprime un User --------------------

        @GetMapping("/user-delete/{id}")
        public String showDeleteUserAdminForm (@PathVariable Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-delete-admin";
        }

        @PostMapping("/user-delete/{id}")
        public String deleteUserAdmin(@PathVariable Integer id) {
            userService.deleteUser(id); // Supprime l'utilisateur avec l'ID passé dans l'URL
            return "redirect:/users/liste";
        }

        //------------------Partie un User se supprime lui même--------------------------------

    @GetMapping("/user-profile/{id}")
    public String showUserProfile(@PathVariable Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-profile";
    }

    @PostMapping("/user-profile/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id); // Supprime l'utilisateur
        return "redirect:/"; // Redirige vers la page d'accueil
    }

    }