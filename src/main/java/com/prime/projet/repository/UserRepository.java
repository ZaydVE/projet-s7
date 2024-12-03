package com.prime.projet.repository;

import com.prime.projet.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
