package com.prime.projet.service;

import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.service.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Créer un utilisateur
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setLastname(userDto.getLastname());
        user.setFirstname(userDto.getFirstname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAdmin(userDto.isAdmin());
        return userRepository.save(user);
    }

    //Supprimer un utilisateur
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
