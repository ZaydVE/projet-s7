package com.prime.projet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                16,  // Longueur de sel (salt length en bytes)
                32,  // Longueur de hash (hash length en bytes)
                1,   // Parallelisme (threads)
                60000,  // Mémoire (en kilobytes)
                10    // Itérations
        );
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","politique-de-confidentialite","cgv","/faq","/about-us","/contact","/reviews", "/styles/**", "/assets/**", "/public", "/favicon.ico", "/users/new","/images/**", "/login", "/destinations","/destinations/{id}", "/destinations/**").permitAll() // Chemins publics
                        .requestMatchers("/users/admin","/users/delete/**", "/users/newadmin", "/users/delete-users","/users/edit-users","/users/list","/destinations/list","/destinations/new","/destinations/edit","/destinations/delete", "/reviews/review-list-admin", "reviews/delete/").hasRole("ADMIN") // ADMIN uniquement
                        .requestMatchers("/users/user-edit-himself", "/users/user-edit-himself-s", "/reviews/review-list", "reviews/delete/").hasRole("USER")
                        .anyRequest().authenticated() // Toute autre requête doit être authentifiée
                )
                .csrf().disable()
                .formLogin((form) -> form
                        .loginPage("/login") // Page de login personnalisée
                        .failureUrl("/login?error=true") // Redirige vers /login avec un paramètre "error" en cas d'échec
                        .permitAll() // Autorise tout le monde à accéder à la page de connexion
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")//url pour la déconnexion
                        .logoutSuccessUrl("/") // Redirection après déconnexion
                        .permitAll()
                );
        return httpSecurity.build();
    }
}