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

    // Exceptions spécifiques avec retour HTML sous forme de modèle

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, Model model) {
        logger.warn("UserAlreadyExistsException: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Un utilisateur avec cet email existe déjà. Veuillez utiliser un email différent.");
        return "error";  // Retourne la vue 'error.html' avec le message d'erreur
    }

    @ExceptionHandler(InvalidFilterException.class)
    public String handleInvalidFilter(InvalidFilterException ex, Model model) {
        logger.warn("InvalidFilterException: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Les filtres fournis sont invalides. Veuillez vérifier votre saisie.");
        return "error";  // Retourne la vue 'error.html' avec le message d'erreur
    }

    @ExceptionHandler(NoDestinationAvailableException.class)
    public String handleNoDestinationAvailable(NoDestinationAvailableException ex, Model model) {
        logger.info("NoDestinationAvailableException: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Aucune destination disponible selon vos critères. Veuillez élargir votre recherche.");
        return "error";  // Retourne la vue 'error.html' avec le message d'erreur
    }


    // Gestion des erreurs inattendues (génériques)
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Une erreur inattendue s'est produite: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "Une erreur inattendue s'est produite. Veuillez réessayer plus tard.");
        return "error"; // Redirige vers "error.html"
    }
}
