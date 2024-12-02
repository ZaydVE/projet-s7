package com.prime.projet.core.spring.repository;

import com.prime.projet.core.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
        User findByEmail(String email);
}