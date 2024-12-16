package com.prime.projet.service;

import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Créer un utilisateur
    public void createUser(UserDto userDto) {
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
        userRepository.deleteById(userId);
    }

    // Récupérer un utilisateur par ID
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
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

    // Mettre à jour un utilisateur
    public void updateUser(Integer id, UserDto userDto) {
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
}