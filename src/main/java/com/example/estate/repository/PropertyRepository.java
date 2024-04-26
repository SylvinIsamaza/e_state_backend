package com.example.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estate.models.Property;

public interface PropertyRepository extends JpaRepository<Property,String> {

}
