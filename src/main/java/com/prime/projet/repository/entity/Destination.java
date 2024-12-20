package com.prime.projet.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer destinationId;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false, length = 45)
    private String continent;

    @Column(nullable = false, length = 45)
    private String country;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String type;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(nullable = false, length = 255)
    private String lienImage;

    @Column(nullable = false)
    private int nbPlaces;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON
    private List<Booking> bookings;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON
    private List<Review> reviews;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON
    private List<Offer> offers;

    @Transient
    private int duration; // Champ temporaire pour la durée (en jours)

    // Méthode pour calculer la durée du séjour
    @PostLoad
    public void calculateDuration() {
        if (startDate != null && endDate != null) {
            long diff = endDate.getTime() - startDate.getTime();
            duration = (int) (diff / (1000 * 60 * 60 * 24)); // Conversion du temps en jours
        }
    }

    // Getters et setters
    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
