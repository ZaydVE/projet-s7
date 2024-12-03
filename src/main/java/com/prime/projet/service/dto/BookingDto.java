package com.prime.projet.service.dto;

import java.util.Date;

public class BookingDto {
    private Integer bookingId;
    private Date bookingDate;
    private int nbPassengers;
    private float totalPrice;
    private Integer userId;
    private Integer destinationId;

    // Getters et Setters
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getNbPassengers() {
        return nbPassengers;
    }

    public void setNbPassengers(int nbPassengers) {
        this.nbPassengers = nbPassengers;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }
}
