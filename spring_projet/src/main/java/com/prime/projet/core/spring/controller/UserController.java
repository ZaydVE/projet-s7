package com.prime.projet.core.spring.controller;

import com.prime.projet.core.data.entity.User;
import com.prime.projet.core.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Créer un utilisateur
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getPhoneNumber());
    }

    // Modifier un utilisateur
    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable Integer userId, @RequestBody User user) {
        return userService.updateUser(userId, user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhoneNumber(), user.getPassword());
    }

    // Supprimer un utilisateur
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }

    // Trouver un utilisateur par email
    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // Trouver un utilisateur par ID
    @GetMapping("/{userId}")
    public User findById(@PathVariable Integer userId) {
        return UserService.findById(userId);
    }
}