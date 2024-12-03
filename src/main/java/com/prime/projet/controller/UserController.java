package com.prime.projet.controller;

import com.prime.projet.service.UserService;
import com.prime.projet.service.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Créer un utilisateur
    @PostMapping("/new")
    public String createUser(UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users/success";
    }

    //Supprimer un utilisateur
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
