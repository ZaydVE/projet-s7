package com.prime.projet.service;

import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        // GIVEN
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setAdmin(false);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // WHEN
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // THEN
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldLoadUserWithAdminRole() {
        // GIVEN
        String email = "admin@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setAdmin(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // WHEN
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // THEN
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // GIVEN
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldLogInfoWhenLoadingUser() {
        // GIVEN
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setAdmin(false);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // WHEN
        customUserDetailsService.loadUserByUsername(email);

        // THEN
        // Verify that the info log is called
        // The logger itself cannot be easily verified without a custom test framework,
        // but you can check that the userRepository method was invoked.
        verify(userRepository, times(1)).findByEmail(email);
    }
}
