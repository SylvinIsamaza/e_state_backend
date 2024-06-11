package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
import com.example.estate.utils.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="User",description = "User APIs")
public class UserController {
    @Autowired
    private UserServiceImplementation customUserDetails;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Login ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully Logged in"),
            @ApiResponse(responseCode = "401", description ="Invalid username or password"),
            @ApiResponse(responseCode = "500",description = "Something went wrong")
    })

    @PostMapping("/auth/login")
    public ResponseEntity<Response> login(@RequestBody AuthRequest request) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(request.getEmail());

        if (userDetails == null) {
           
            Response<String> response = new Response<String>(false, "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch" + userDetails);
            Response<String> response = new Response<String>(false, "Invalid username or password");
            return ResponseEntity.status(401).body(response);
            // throw new BadCredentialsException("Invalid username or password");
            
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
        Response<AuthResponse> response = new Response<AuthResponse>(false, authResponse);
        return ResponseEntity.ok().headers(headers).body(response);
    }
    @Operation(summary = "Sign up ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully Registered"),
            @ApiResponse(responseCode = "500", description = "Register failed")
    })
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
    @Operation(summary = "Get user by Id ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "Something went wrong"),
            @ApiResponse(responseCode = "401",description = "User not found")
    })
    @GetMapping("/user/{id}")
    Optional<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @Operation(summary = "Authentication")
    @Parameter(name = "Authorization", description = "Token received from login", required = true, in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials"),
        @ApiResponse(responseCode ="500",description = "Something went wrong")
    })
    @GetMapping("/authenticate")
    public ResponseEntity<Response> getUserByJwt() {
       try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("bad credentials");
            Response<String> response = new Response<String>(false, "Invalid credentials"); 
            // Handle the case where the provided token does not match the authenticated token
            return ResponseEntity.status(403).body(response);
        }
        User user = userRepository.findByEmail(authentication.getPrincipal().toString());
        Response<User> userResponse = new Response<User>(true, user);
        return ResponseEntity.status(200).body(userResponse);
       } catch (Exception e) {
           Response<String> response = new Response<String>(false, "Something went wrong");
           return ResponseEntity.status(500).body(response);
       }
        
    }
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode ="500",description = "Something went wrong")
    })
    @GetMapping("/user")
    List<User> getAllUser() {
        return userRepository.findAll();
    }
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode ="500",description = "Something went wrong")
    })

    @DeleteMapping("/user/{id}")
    void DeleteUserById(@PathVariable String id) {
        userRepository.deleteById(id);
    }
    @Operation(summary = "Delete all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode ="500",description = "Something went wrong")
    })
    @DeleteMapping("/user")
    void DeleteAllUsers() {
        userRepository.deleteAll();
    }
    @Operation(summary = "Update user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials"),
        @ApiResponse(responseCode ="500",description = "Something went wrong")
    })
    @PostMapping("/user/{id}")
    ResponseEntity<Response<User>> updateUserById(@RequestBody User userPayload, @PathVariable String id) {
        User userOptional = userRepository.findById(id).orElse(null);
        if (userOptional != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal().toString().equals(userOptional.getEmail().toString())) {
                if (userPayload.getFullName() != null) {
                    userOptional.setFullName(userPayload.getFullName());
                }
                if (userPayload.getAge() != 0) {
                    userOptional.setAge(userPayload.getAge());
                }
                if (userPayload.getAddress() != null) {
                    userOptional.setAddress(userPayload.getAddress());
                }
                if (userPayload.isVerified()) {
                    userOptional.setVerified(userPayload.isVerified());
                }
                if (userPayload.getPhoneNumber() != null) {
                    userOptional.setPhoneNumber(userPayload.getPhoneNumber());
                }
                if (userPayload.getAvatar() != null) {
                    userOptional.setAvatar(userPayload.getAvatar());
                }
                if (userPayload.getRole() != null) {
                    userOptional.setRole(userPayload.getRole());
                }
                userRepository.save(userOptional);
                Response<User> response = new Response<User>(false, userOptional, "User updated successfully");
                return ResponseEntity.ok().body(response);

            }
            else {
                Response<User> response = new Response<User>(false, null, "Invalid credentials");
                return ResponseEntity.status(403).body(response);
        }
        }
        else {
            Response<User> response = new Response<User>(false, null, "User not found");
            return ResponseEntity.status(404).body(response);
        }
        
    }

}
