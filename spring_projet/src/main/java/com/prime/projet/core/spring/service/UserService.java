package com.prime.projet.core.spring.service;

import com.prime.projet.core.data.entity.User;
import com.prime.projet.core.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private static UserRepository userRepository;

    // Créer un nouvel utilisateur
    public User createUser(String email, String password, String firstname, String lastname, String phoneNumber) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);  // On stocke désormais le mot de passe en clair (ceci est un exemple, à éviter en production)
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhoneNumber(phoneNumber);
        user.setCreatedAt(new java.util.Date());
        user.setAdmin(false); // Par défaut, l'utilisateur n'est pas un admin
        return userRepository.save(user);
    }

    // Modifier les informations personnelles d'un utilisateur
    public User updateUser(Integer userId, String firstname, String lastname, String email, String phoneNumber, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);  // On met à jour le mot de passe sans le hasher
        }
        return userRepository.save(user);
    }

    // Supprimer un utilisateur
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        userRepository.delete(user);
    }

    // Trouver un utilisateur par email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Trouver un utilisateur par email
    public static User findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}