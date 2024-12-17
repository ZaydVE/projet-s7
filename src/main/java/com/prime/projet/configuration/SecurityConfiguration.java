package com.prime.projet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .requestMatchers("/","/reviews/liste", "/styles/**", "/assets/**", "/public", "/favicon.ico", "/users/new", "/destinations").permitAll() // Chemins publics
                        .requestMatchers("/users/delete/**","/destinations/new", "/users/newadmin", "/users/admin/success", "/users/delete-users","/users/edit-users").hasRole("ADMIN") // ADMIN uniquement
                        .requestMatchers("/users/inscription-success", "/users/user-edit-himself", "/users/user-edit-himself-s").hasRole("USER")
                        .anyRequest().authenticated() // Toute autre requête doit être authentifiée
                )
                .csrf().disable()
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Pour le test uniquement : mots de passe en clair
        return NoOpPasswordEncoder.getInstance();
    }
    */
}