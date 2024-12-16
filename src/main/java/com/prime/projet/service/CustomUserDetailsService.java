package com.prime.projet.service;

import com.prime.projet.repository.UserRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
