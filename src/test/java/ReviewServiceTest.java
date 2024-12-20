package com.prime.projet.service;

import com.prime.projet.exception.ReviewNotFoundException;
import com.prime.projet.repository.DestinationRepository;
import com.prime.projet.repository.ReviewRepository;
import com.prime.projet.repository.entity.Destination;
import com.prime.projet.repository.entity.Review;
import com.prime.projet.controller.dto.ReviewDto;
import com.prime.projet.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview() {
        // Given
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setDestinationId(1);
        reviewDto.setTitle("Great Place");
        reviewDto.setComment("I had a great time!");
        reviewDto.setRating(5);

        Destination destination = new Destination();
        destination.setDestinationId(1);

        when(destinationRepository.findById(1)).thenReturn(Optional.of(destination));
        when(user.getUserId()).thenReturn(1);

        // When
        reviewService.createReview(reviewDto, user);

        // Then
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testCreateReview_DestinationNotFound() {
        // Given
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setDestinationId(1);
        reviewDto.setTitle("Great Place");
        reviewDto.setComment("I had a great time!");
        reviewDto.setRating(5);

        when(destinationRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> reviewService.createReview(reviewDto, user));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void testGetReviewsForDestination() {
        // Given
        Integer destinationId = 1;
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByDestinationDestinationId(destinationId)).thenReturn(reviews);

        // When
        List<Review> result = reviewService.getReviewsForDestination(destinationId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByDestinationDestinationId(destinationId);
    }

    @Test
    void testGetAllReviews() {
        // Given
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(reviews);

        // When
        List<Review> result = reviewService.getAllReviews();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testDeleteReviewById() {
        // Given
        Integer reviewId = 1;
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        // When
        reviewService.deleteReviewById(reviewId);

        // Then
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void testDeleteReviewById_ReviewNotFound() {
        // Given
        Integer reviewId = 1;
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> reviewService.deleteReviewById(reviewId));
        verify(reviewRepository, never()).deleteById(reviewId);
    }

    @Test
    void testGetReviewById() {
        // Given
        Integer reviewId = 1;
        Review review = new Review();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // When
        Review result = reviewService.getReviewById(reviewId);

        // Then
        assertNotNull(result);
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void testGetReviewById_ReviewNotFound() {
        // Given
        Integer reviewId = 1;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewById(reviewId));
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void testGetReviewsByUser() {
        // Given
        Integer userId = 1;
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByUserUserId(userId)).thenReturn(reviews);

        // When
        List<Review> result = reviewService.getReviewsByUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByUserUserId(userId);
    }
}
