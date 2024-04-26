package com.example.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.estate.models.User;

public interface UserRepository extends JpaRepository<User,String> {
@Query("SELECT u FROM User u WHERE u.email = ?1")
User findByEmail(String email);
}
