package com.prime.projet.controller;

import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.ReviewService;
import com.prime.projet.service.UserService;
import com.prime.projet.service.dto.ReviewDto;
import com.prime.projet.service.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import java.security.Principal;
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
        public String showUserProfile () {
            return "user-profile"; // Fichier user-profile.html dans templates
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
        public String registerUser (@ModelAttribute UserDto userDto){
            userService.createUser(userDto);
            return "redirect:/users/inscription-success";
        }

        @GetMapping("/inscription-success")
        public String successPage () {
            return "inscription-success";
        }

        // -------------------- ADMIN CREE UN UTILISATEUR OU UN ADMIN --------------------
        @GetMapping("/newadmin")
        public String showAdminRegistrationForm () {
            return "user-inscription-admin";
        }

        @PostMapping("/newadmin")
        public String registerUserAsAdmin (@ModelAttribute UserDto userDto){
            userDto.setAdmin(true); // Assurez-vous que l'utilisateur est marqué comme administrateur
            userService.createUser(userDto);
            return "redirect:/users/admin/success";
        }

        @GetMapping("/admin/success")
        public String successPageAdmin () {
            return "inscription-success";
        }

        // -------------------- Partie User Modifie ses Informations --------------------

        @GetMapping("/user-edit-himself")
        public String editHimselfUserForm () {
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


        // -------------------- Partie Admin Modifie un User ----------------------------
        //Ouvrir la page admin
        @GetMapping("/admin")
        public String showAdminPage () {
            return "admin";
        }

        // -------------------- Partie Modifier un User --------------------
        @GetMapping("/user-edit")
        public String editUserForm () {
            return "user-edit";
        }

        @PostMapping("/user-edit")
        public String updateUser (@ModelAttribute UserDto userDto){
            userService.updateUser(userDto.getUserId(), userDto);
            return "user-edit-success";
        }

        //-------------------- Partie Supprimer un User --------------------

        @GetMapping("/user-delete")
        public String showDeleteUserForm () {
            return "user-delete";
        }

        @PostMapping("/user-delete")
        public String deleteUser (@RequestParam("userId") Integer userId){
            userService.deleteUser(userId); //
            return "user-delete-success";
        }
    }