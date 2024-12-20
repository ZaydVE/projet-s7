package com.prime.projet.service;

import com.prime.projet.exception.UserAlreadyExistsException;
import com.prime.projet.exception.UserNotFoundException;
import com.prime.projet.repository.UserRepository;
import com.prime.projet.repository.entity.User;
import com.prime.projet.controller.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private User user;

    @Mock
    private UserDto userDto;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        var result = userService.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setLastname("Doe");
        userDto.setFirstname("John");
        userDto.setPassword("password123");
        userDto.setPhonenumber("1234567890");
        userDto.setAdmin(true);

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        // When
        userService.createUser(userDto);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_AlreadyExists() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));

        // When & Then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Given
        Integer userId = 1;
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUser() {
        // Given
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setFirstname("Jane");
        userDto.setLastname("Doe");
        userDto.setEmail("jane.doe@example.com");
        userDto.setPhonenumber("0987654321");
        userDto.setAdmin(false);
        userDto.setPassword("newPassword123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        // When
        userService.updateUser(userId, userDto);

        // Then
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("Jane", existingUser.getFirstname());
        assertEquals("Jane", existingUser.getFirstname());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Given
        Integer userId = 1;
        UserDto userDto = new UserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertDoesNotThrow(() -> userService.updateUser(userId, userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindById() {
        // Given
        Integer userId = 1;
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // When
        User result = userService.findById(userId);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_UserNotFound() {
        // Given
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetCurrentUser() {
        // Simuler l'utilisateur connecté dans le SecurityContext
        org.springframework.security.core.userdetails.User principal = mock(org.springframework.security.core.userdetails.User.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn("test@example.com");

        // Simuler la récupération de l'utilisateur dans le repository
        User currentUser = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(currentUser));

        // When
        User result = userService.getCurrentUser();

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testUserNotFoundException() {
        // Simuler l'utilisateur connecté dans le SecurityContext
        org.springframework.security.core.userdetails.User principal = mock(org.springframework.security.core.userdetails.User.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn("test@example.com");

        // Simuler la situation où l'utilisateur n'est pas trouvé dans le repository
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.getCurrentUser());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }


    @Test
    void testGetCurrentUser_NotFound() {
        // Given
        org.springframework.security.core.userdetails.User principal = mock(org.springframework.security.core.userdetails.User.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.getCurrentUser());
    }
}
