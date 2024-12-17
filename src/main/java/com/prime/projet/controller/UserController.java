package com.prime.projet.controller;

import com.prime.projet.service.UserService;
import com.prime.projet.service.dto.UserDto;
import org.springframework.stereotype.Controller;
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
        return "redirect:/users/inscription-success";
    }

    @GetMapping("/inscription-success")
    public String successPage() {
        return "inscription-success";
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
    @GetMapping("/user-edit")
    public String editUserForm() {
        return "user-edit";
    }

    @PostMapping("/user-edit")
    public String updateUser(@ModelAttribute UserDto userDto) {
        userService.updateUser(userDto.getUserId(), userDto);
        return "user-edit-success";
    }

    //-------------------- Partie Supprimer un User --------------------

    @GetMapping("/user-delete")
    public String showDeleteUserForm() {
        return "user-delete";
    }

    @PostMapping("/user-delete")
    public String deleteUser(@RequestParam("userId") Integer userId) {
        userService.deleteUser(userId); //
        return "user-delete-success";
    }
}