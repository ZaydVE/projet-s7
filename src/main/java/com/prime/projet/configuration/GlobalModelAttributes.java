package com.prime.projet.configuration;

import com.prime.projet.service.DestinationService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final DestinationService destinationService;

    public GlobalModelAttributes(DestinationService destinationService) {
        this.destinationService = destinationService;
    }
}
