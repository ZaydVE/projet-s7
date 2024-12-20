package com.prime.projet.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Exceptions spécifiques avec redirection HTML
    @ExceptionHandler(BookingNotFoundException.class)
    public String handleBookingNotFound(BookingNotFoundException ex, Model model) {
        logger.warn("BookingNotFoundException: {}", ex.getMessage());
        model.addAttribute("errorMessage", "La réservation demandée est introuvable. Détails : " + ex.getMessage());
        return "error"; // Redirige vers la vue "error.html"
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        logger.warn("UserNotFoundException: {}", ex.getMessage());
        model.addAttribute("errorMessage", "L'utilisateur recherché n'a pas été trouvé. Détails : " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    public String handleDestinationNotFound(DestinationNotFoundException ex, Model model) {
        logger.warn("DestinationNotFoundException: {}", ex.getMessage());
        model.addAttribute("errorMessage", "La destination demandée est introuvable. Veuillez vérifier les informations fournies.");
        return "error";
    }

    @ExceptionHandler(FileStorageException.class)
    public String handleFileStorage(FileStorageException ex, Model model) {
        logger.error("FileStorageException: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "Une erreur est survenue lors de la gestion du fichier. Détails : " + ex.getMessage());
        return "error";
    }

    // Exceptions spécifiques avec réponse JSON
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        logger.warn("UserAlreadyExistsException: {}", ex.getMessage());
        String errorMessage = "Un utilisateur avec cet email existe déjà. Veuillez utiliser un email différent.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<String> handleInvalidFilter(InvalidFilterException ex) {
        logger.warn("InvalidFilterException: {}", ex.getMessage());
        String errorMessage = "Les filtres fournis sont invalides. Veuillez vérifier votre saisie.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(NoDestinationAvailableException.class)
    public ResponseEntity<String> handleNoDestinationAvailable(NoDestinationAvailableException ex) {
        logger.info("NoDestinationAvailableException: {}", ex.getMessage());
        String errorMessage = "Aucune destination disponible selon vos critères. Veuillez élargir votre recherche.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    // Gestion des erreurs inattendues (génériques)
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Une erreur inattendue s'est produite: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "Une erreur inattendue s'est produite. Veuillez réessayer plus tard.");
        return "error"; // Redirige vers "error.html"
    }
}
