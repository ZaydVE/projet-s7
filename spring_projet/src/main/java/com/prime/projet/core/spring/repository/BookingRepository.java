package com.prime.projet.core.spring.repository;

import com.prime.projet.core.data.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}