package com.example.estate.models;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.TransactionScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id     
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date dob;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String avatar;
    private String role;
    private boolean isVerified;
    private String fullName;
 @JsonIgnore
    @OneToOne(mappedBy = "user")
    Agent agent;

    
    private int age;

    public User(String fullName, String email, String phoneNumber, String password, String address, String avatar,
            String role, boolean isVerified,Date dob) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
        this.role = role;
        this.isVerified = isVerified;
        this.dob = dob;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
