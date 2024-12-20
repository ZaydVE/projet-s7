package com.prime.projet.controller;

import com.prime.projet.repository.entity.Destination;
import com.prime.projet.service.DestinationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;
    private static final Logger logger = LoggerFactory.getLogger(DestinationController.class);


    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    //Affiche toutes les destinations
    @GetMapping
    public String showAllDestinations(Model model) {
        logger.info("Displaying all destinations.");
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        return "destinations";
    }

    //Affiche les détails d'une destination
    @GetMapping("/{id}")
    public String getDestinationDetails(@PathVariable Integer id, Model model) {
        logger.info("Fetching details for destination ID: {}", id);
        Destination destination = destinationService.getDestinationById(id);
        model.addAttribute("destination", destination);
        return "destination-details";
    }

    //Affiche une page pour créer une nouvelle destination
    @GetMapping("/new")
    public String showCreateDestinationForm() {
        logger.info("Displaying create destination form.");
        return "destination-form";
    }

    @PostMapping("/new")
    public String createDestination(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") float price,
            @RequestParam("continent") String continent,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("type") String type,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("nbPlaces") int nbPlaces,
            @RequestParam("lienImage") MultipartFile lienImage,
            RedirectAttributes redirectAttributes) {

        logger.info("Creating a new destination.");

        destinationService.createDestination(
                name, description, price, continent, country, city, type, startDate, endDate, nbPlaces, lienImage
        );

        redirectAttributes.addFlashAttribute("successMessageCreateDestination", "Ajout de la destination réussi !");

        return "redirect:/destinations";
    }

    @GetMapping("/edit/{id}")
    public String editDestinationForm(@PathVariable Integer id, Model model) {
        logger.info("Loading edit form for destination ID: {}", id);
        Destination destination = destinationService.getDestinationById(id);

        // Liste des continents en dur
        List<String> continents = List.of(
                "Afrique",
                "Amérique du Nord",
                "Amérique du Sud",
                "Asie",
                "Europe",
                "Océanie"
        );

        // Liste des pays en dur
        List<String> countries = List.of(
                "Afghanistan", "Afrique du Sud", "Albanie", "Algérie", "Allemagne",
                "Andorre", "Angola", "Antigua-et-Barbuda", "Arabie Saoudite", "Argentine",
                "Arménie", "Australie", "Autriche", "Azerbaïdjan", "Bahamas", "Bahreïn",
                "Bangladesh", "Barbade", "Belau / Palau / Palaos", "Belgique", "Belize",
                "Bénin", "Bhoutan", "Biélorussie", "Birmanie", "Bolivie",
                "Bosnie-Herzégovine", "Botswana", "Brésil", "Brunei", "Bulgarie",
                "Burkina Faso", "Burundi", "Cambodge", "Cameroun", "Canada", "Cap-Vert",
                "Centrafrique", "Chili", "Chine", "Chypre", "Colombie", "Comores",
                "Congo-Brazzaville", "Congo-Kinshasa", "Corée du Nord", "Corée du Sud",
                "Costa Rica", "Côte d’Ivoire", "Croatie", "Cuba", "Danemark", "Djibouti",
                "Dominique", "Égypte", "Émirats arabes unis", "Équateur", "Érythrée",
                "Espagne", "Estonie", "États-Unis", "Éthiopie", "Fidji", "Finlande",
                "France", "Gabon", "Gambie", "Géorgie", "Ghana", "Grèce", "Grenade",
                "Guatemala", "Guinée-Bissau", "Guinée-Conakry", "Guinée équatoriale",
                "Guyana", "Haïti", "Honduras", "Hongrie", "Inde", "Indonésie", "Irak",
                "Iran", "Irlande", "Islande", "Israël", "Italie", "Jamaïque", "Japon",
                "Jordanie", "Kazakhstan", "Kenya", "Kirghizistan", "Kiribati", "Kosovo",
                "Koweït", "Laos", "Lesotho", "Lettonie", "Liban", "Liberia", "Libye",
                "Liechtenstein", "Lituanie", "Luxembourg", "Macédoine", "Madagascar",
                "Malaisie", "Malawi", "Maldives", "Mali", "Malte", "Maroc", "Marshall",
                "Maurice", "Mauritanie", "Mexique", "Micronésie", "Moldavie", "Monaco",
                "Mongolie", "Monténégro", "Mozambique", "Namibie", "Nauru", "Népal",
                "Nicaragua", "Niger", "Nigeria", "Norvège", "Nouvelle-Zélande", "Oman",
                "Ouganda", "Ouzbékistan", "Pakistan", "Panama", "Papouasie-Nouvelle-Guinée",
                "Paraguay", "Pays-Bas", "Pérou", "Philippines", "Pologne", "Portugal",
                "Qatar", "République dominicaine", "République tchèque", "Roumanie",
                "Royaume-Uni", "Russie", "Rwanda", "Saint-Christophe-et-Niévès",
                "Sainte-Lucie", "Saint-Marin", "Saint-Vincent-et-les-Grenadines",
                "Salomon", "São-Tomé-et-Príncipe", "Salvador", "Samoa occidentales",
                "Sénégal", "Serbie", "Seychelles", "Sierra Leone", "Singapour",
                "Slovaquie", "Slovénie", "Somalie", "Soudan", "Soudan du Sud",
                "Sri Lanka", "Suède", "Suisse", "Surinam", "Swaziland", "Syrie",
                "Tadjikistan", "Tanzanie", "Tchad", "Timor oriental", "Thaïlande",
                "Togo", "Tonga", "Trinité-et-Tobago", "Tunisie", "Turkménistan",
                "Turquie", "Tuvalu", "Ukraine", "Uruguay", "Vanuatu", "Vatican",
                "Venezuela", "Vietnam", "Yémen", "Zambie", "Zimbabwe"
        );

        model.addAttribute("destination", destination);
        model.addAttribute("continents", continents);
        model.addAttribute("countries", countries);
        return "destination-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateDestination(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam float price,
            @RequestParam String continent,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String type,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int nbPlaces,
            @RequestParam(value = "lienImage", required = false) MultipartFile lienImage,
            RedirectAttributes redirectAttributes) {
        logger.info("Updating destination with ID: {}", id);

        // Vérifier si une image a été uploadée
        if (lienImage != null && !lienImage.isEmpty()) {
            // Sauvegarder l'image et récupérer son chemin
            String imagePath = destinationService.saveImage(lienImage);

            // Mettre à jour avec une nouvelle image
            destinationService.updateDestination(
                    id, name, description, price, continent, country, city, type,
                    startDate, endDate, nbPlaces, lienImage
            );
        } else {
            // Mettre à jour sans changer l'image
            destinationService.updateDestinationWithoutImage(
                    id, name, description, price, continent, country, city, type,
                    startDate, endDate, nbPlaces
            );
        }

        redirectAttributes.addFlashAttribute("successMessageUpdateDestination", "Modification de la destination réussie !");
        return "redirect:/destinations";
    }

    @GetMapping("/delete/{destinationId}")
    public String showDeleteForm(@PathVariable("destinationId") Integer destinationId, Model model) {
        Destination destination = destinationService.getDestinationById(destinationId);
        model.addAttribute("destination", destination);
        return "destination-delete"; // Nom du fichier HTML contenant le formulaire
    }

    @PostMapping("/delete/{destinationId}")
    public String deleteDestination(@PathVariable("destinationId") Integer destinationId, RedirectAttributes redirectAttributes) {
        logger.info("Deleting destination with ID: {}", destinationId);
        destinationService.deleteDestination(destinationId);
        redirectAttributes.addFlashAttribute("successMessageDeleteDestination", "Suppression de la destination réussie !");
        return "redirect:/destinations";
    }

    @GetMapping("/list")
    public String listAllDestinations(Model model) {
        logger.info("Listing all destinations.");
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        return "destination-list";
    }

    @ResponseBody
    @GetMapping("/filter")
    public List<Destination> filterDestinations(
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String pays,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer personnes,
            @RequestParam(required = false) String budget,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String startDateUser,
            @RequestParam(required = false) String endDateUser) {  // Paramètre pour la date de fin de séjour de l'utilisateur

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedStartDate = startDate != null ? LocalDate.parse(startDate, formatter) : null;
        LocalDate parsedEndDate = endDate != null ? LocalDate.parse(endDate, formatter) : null;
        LocalDate parsedStartDateUser = startDateUser != null ? LocalDate.parse(startDateUser, formatter) : null;  // Convertir la date de l'utilisateur
        LocalDate parsedEndDateUser = endDateUser != null ? LocalDate.parse(endDateUser, formatter) : null;  // Convertir la date de fin de l'utilisateur

        return destinationService.filterDestinations(continent, pays, parsedStartDate, parsedEndDate, personnes, budget, duration, parsedStartDateUser, parsedEndDateUser);
    }
}