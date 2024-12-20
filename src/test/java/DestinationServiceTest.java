package com.prime.projet.service;

import com.prime.projet.exception.DestinationNotFoundException;
import com.prime.projet.exception.InvalidFilterException;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.entity.Destination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private MultipartFile image;

    @InjectMocks
    private DestinationService destinationService;

    private static final String UPLOAD_DIR = "C:/uploads";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDestinations() {
        // Given
        List<Destination> destinations = Arrays.asList(new Destination(), new Destination());
        when(destinationRepository.findAll()).thenReturn(destinations);

        // When
        List<Destination> result = destinationService.getAllDestinations();

        // Then
        assertEquals(2, result.size());
        verify(destinationRepository, times(1)).findAll();
    }

    @Test
    void testGetDestinationById_ExistingDestination() {
        // Given
        Integer destinationId = 1;
        Destination destination = new Destination();
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.of(destination));

        // When
        Destination result = destinationService.getDestinationById(destinationId);

        // Then
        assertNotNull(result);
        verify(destinationRepository, times(1)).findById(destinationId);
    }

    @Test
    void testGetDestinationById_NonExistingDestination() {
        // Given
        Integer destinationId = 1;
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DestinationNotFoundException.class, () -> destinationService.getDestinationById(destinationId));
        verify(destinationRepository, times(1)).findById(destinationId);
    }

    @Test
    void testFilterDestinations_InvalidDateRange() {
        // Given
        String continent = "Europe";
        String pays = "France";
        LocalDate startDate = LocalDate.of(2024, 12, 20);
        LocalDate endDate = LocalDate.of(2024, 12, 19);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> destinationService.filterDestinations(continent, pays, startDate, endDate, 2, "medium", "short", null, null));
    }

    @Test
    void testFilterDestinations_ValidCriteria() {
        // Given
        String continent = "Europe";
        String pays = "France";
        LocalDate startDate = LocalDate.of(2024, 12, 20);
        LocalDate endDate = LocalDate.of(2024, 12, 30);
        when(destinationRepository.findWithFilters(anyString(), anyString(), any(), any(), anyInt(), anyString(), anyString(), any(), any())).thenReturn(Arrays.asList(new Destination()));

        // When
        List<Destination> result = destinationService.filterDestinations(continent, pays, startDate, endDate, 2, "medium", "short", null, null);

        // Then
        assertNotNull(result);
        verify(destinationRepository, times(1)).findWithFilters(continent, pays, startDate, endDate, 2, "medium", "short", null, null);
    }

    @Test
    void testFindTop4ClosestDestinations() {
        // Given
        when(destinationRepository.findTop4ByStartDateAfterOrderByStartDateAsc(any())).thenReturn(Arrays.asList(new Destination(), new Destination(), new Destination(), new Destination()));

        // When
        List<Destination> result = destinationService.findTop4ClosestDestinations();

        // Then
        assertNotNull(result);
        assertEquals(4, result.size());
        verify(destinationRepository, times(1)).findTop4ByStartDateAfterOrderByStartDateAsc(any());
    }

    @Test
    void testCreateDestination() throws IOException {
        // Given
        String name = "Paris";
        String description = "City of Lights";
        float price = 1000.0f;
        String continent = "Europe";
        String country = "France";
        String city = "Paris";
        String type = "Adventure";
        String startDate = "2024-12-20";
        String endDate = "2024-12-30";
        int nbPlaces = 10;

        // Mock MultipartFile
        when(image.getOriginalFilename()).thenReturn("paris.jpg");
        when(image.getBytes()).thenReturn("image content".getBytes());

        // When
        destinationService.createDestination(name, description, price, continent, country, city, type, startDate, endDate, nbPlaces, image);

        // Then
        verify(destinationRepository, times(1)).save(any(Destination.class));
    }

    @Test
    void testUpdateDestination() throws IOException {
        // Given
        Integer destinationId = 1;
        Destination destination = new Destination();
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.of(destination));
        String newName = "Updated Paris";
        String description = "Updated description";
        float price = 1200.0f;
        String continent = "Europe";
        String country = "France";
        String city = "Paris";
        String type = "Luxury";
        String startDate = "2024-12-25";
        String endDate = "2024-12-31";
        int nbPlaces = 12;

        // Mock MultipartFile
        when(image.getOriginalFilename()).thenReturn("updated_paris.jpg");
        when(image.getBytes()).thenReturn("new image content".getBytes());

        // When
        destinationService.updateDestination(destinationId, newName, description, price, continent, country, city, type, startDate, endDate, nbPlaces, image);

        // Then
        verify(destinationRepository, times(1)).save(any(Destination.class));
    }

    @Test
    void testDeleteDestination() throws IOException {
        // Given
        Integer destinationId = 1;
        Destination destination = new Destination();
        destination.setLienImage("path/to/image.jpg");
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.of(destination));

        // Mock file deletion (simulate no exception)
        Path path = Path.of("path/to/image.jpg");
        when(Files.deleteIfExists(path)).thenReturn(true);

        // When
        destinationService.deleteDestination(destinationId);

        // Then
        verify(destinationRepository, times(1)).deleteById(destinationId);
    }

    @Test
    void testDeleteDestination_NotFound() {
        // Given
        Integer destinationId = 1;
        when(destinationRepository.findById(destinationId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DestinationNotFoundException.class, () -> destinationService.deleteDestination(destinationId));
        verify(destinationRepository, never()).deleteById(destinationId);
    }
}
