package com.example.estate.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;
  // @OneToOne(mappedBy = "user")
  // User user;
  String description;
  int stars;
  int status;
  String review;
}
