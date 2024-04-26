package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.User;
import com.example.estate.repository.UserRepository;
import com.example.estate.security.JwtProvider;
import com.example.estate.security.UserServiceImplementation;
import com.example.estate.utils.AuthRequest;
import com.example.estate.utils.AuthResponse;

@RestController
public class UserController {
    @Autowired
    private UserServiceImplementation customUserDetails;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(request.getEmail());

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch" + userDetails);

            throw new BadCredentialsException("Invalid username or password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, request.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        User user = userRepository.findByEmail(request.getEmail());
        authResponse.setUser(user);
        user.setPassword("");
        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        ResponseCookie authCookie = ResponseCookie.from("token", token).maxAge(6800).build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authCookie.toString());

        return ResponseEntity.ok().headers(headers).body(authResponse);
    }

    @PostMapping("/user/register")
    ResponseEntity<AuthResponse> register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserDetails userDetails = customUserDetails.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        user.setPassword("");
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Register success");
        authResponse.setJwt(token);
        authResponse.setUser(user);
        authResponse.setStatus(true);

        ResponseCookie authCookie = ResponseCookie.from("token", token).maxAge(6800).build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authCookie.toString());

        return ResponseEntity.ok().headers(headers).body(authResponse);
    }

    @GetMapping("/user/{id}")
    Optional<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<User> getUserByJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("bad credentials");
            // Handle the case where the provided token does not match the authenticated token
            return ResponseEntity.status(403).body(null);
        }

        User user = userRepository.findByEmail(authentication.getPrincipal().toString());
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping("/user")
    List<User> getAllUser() {
        return userRepository.findAll();
    }

    @DeleteMapping("/user/{id}")
    void DeleteUserById(@PathVariable String id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("/user")
    void DeleteAllUsers() {
        userRepository.deleteAll();
    }

}
