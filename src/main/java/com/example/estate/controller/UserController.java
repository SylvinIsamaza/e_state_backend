package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.User;
import com.example.estate.services.UserRepository;

@RestController
public class UserController {
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user/login")
    User login(@RequestBody String email, @RequestBody String password) {
        // User loggedInUser=userRepository.findBy(u, null)
        return null;

    }

    @PostMapping("/user/register")
    User register(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("/user/{id}")
    Optional<User> getUserById(@PathVariable String userId) {
        return userRepository.findById(userId);
    }
    @GetMapping("/user")
    List <User> getAllUser(){
        return userRepository.findAll();
    }
    @DeleteMapping("/user/{id}")
    void DeleteUserById(@PathVariable String id){
        userRepository.deleteById(id);
    }
    @DeleteMapping("/user")
    void DeleteAllUsers(){
        userRepository.deleteAll();
    }
    



}
