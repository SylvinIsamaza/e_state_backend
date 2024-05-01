package com.example.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estate.models.Review;

public interface ReviewRepository extends JpaRepository<Review,String> {

  
} 