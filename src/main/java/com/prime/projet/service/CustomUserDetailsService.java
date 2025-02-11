package com.prime.projet.service;

import com.prime.projet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user details by email: {}", email);
        // Trouver l'utilisateur par email
        com.prime.projet.repository.entity.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Créer les autorités basées sur le champ `admin`
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Tout le monde a ce rôle par défaut
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Ajouter ROLE_ADMIN si admin = true
        }

        // Retourner une instance de UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Nom d'utilisateur (email ici)
                user.getPassword(), // Mot de passe encodé
                authorities // Liste des autorisations
        );
    }
}
