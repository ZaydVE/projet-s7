package com.prime.projet.service;

import com.prime.projet.exception.UserAlreadyExistsException;
import com.prime.projet.exception.UserNotFoundException;
import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.controller.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {    

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Lister tous les users
    public List<User> getAllUsers() {
        logger.info("Fetching all users.");
        return userRepository.findAll();
    }

    // Créer un utilisateur
    public void createUser(UserDto userDto) {
        logger.info("Creating user with email: {}", userDto.getEmail());
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Un utilisateur avec cet email existe déjà.");
        }
        User user = new User();
        user.setLastname(userDto.getLastname());
        user.setFirstname(userDto.getFirstname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAdmin(userDto.isAdmin());
        user.setPhoneNumber(userDto.getPhonenumber());
        user.setCreatedAt(new Date());
        userRepository.save(user);
    }

    // Supprimer un utilisateur
    public void deleteUser(Integer userId) {
        logger.info("Deleting user with ID: {}", userId);
        userRepository.deleteById(userId);
    }

    // Mettre à jour un utilisateur
    public void updateUser(Integer id, UserDto userDto) {
        logger.info("Updating user with ID: {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setPhoneNumber(userDto.getPhonenumber());
            user.setAdmin(userDto.isAdmin());
            if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userRepository.save(user);
        }
    }

    public UserDto createDto(User user){
        if (user == null) {
            return null; // Retourne `null` si l'utilisateur n'existe pas
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setLastname(user.getLastname());
        userDto.setFirstname(user.getFirstname());
        userDto.setEmail(user.getEmail());
        userDto.setPhonenumber(user.getPhoneNumber());
        userDto.setAdmin(user.isAdmin());
        return userDto;
    }

    public User findById(Integer id) {
        logger.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur avec l'ID " + id + " introuvable."));
    }

    public User getCurrentUser() {
        logger.info("Fetching current logged-in user.");
        // Récupérer l'utilisateur connecté
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer l'utilisateur depuis la base de données
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}