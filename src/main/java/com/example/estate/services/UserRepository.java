package com.example.estate.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estate.models.User;

public interface UserRepository extends JpaRepository<User,String> {

}
