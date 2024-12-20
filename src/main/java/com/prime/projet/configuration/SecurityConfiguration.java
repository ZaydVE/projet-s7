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
                        .requestMatchers( "/styles/**", "/assets/**","/favicon.ico","/images/**","/public","/","/login","/about-us", "/cgv", "/contact", "/destinations", "/destinations/{id}", "/error", "/faq", "/politique-de-confidentialite", "/users/new","/reviews" ).permitAll()
                        .requestMatchers("/bookings/delete/**", "/bookings/edit/**", "/bookings/new/**", "/bookings/user-list", "/reviews/delete/{id}", "/reviews/new", "/reviews/review-list", "/users/user-delete/{id}", "/users/user-edit-himself", "/users/user-profile/{id}").hasRole("USER")
                        .requestMatchers("/bookings/delete/**", "/bookings/edit/**", "/bookings/new/**", "/bookings/list", "/destinations/delete/**","/destinations/edit/**", "/destinations/new", "/destinations/list", "/reviews/delete/**", "/reviews/review-list-admin", "/users/user-delete/**", "/users/user-edit/", "/users/newadmin", "/users/list", "/users/user-profile/{id}").hasRole("ADMIN")
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