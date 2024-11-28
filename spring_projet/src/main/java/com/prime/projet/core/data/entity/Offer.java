package com.prime.projet.core.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;

    @Column(name = "percentage_discount", nullable = false)
    private Float percentageDiscount;

    @OneToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Float getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(Float percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}
