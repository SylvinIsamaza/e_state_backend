package com.example.estate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String avatar;
    private String role;
    private boolean isVerified;
    private String fullName;
    @OneToOne(mappedBy = "user")
    Agent agent;

    public User(String fullName, String email, String phoneNumber, String password, String address, String avatar,
            String role, boolean isVerified) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
        this.role = role;
        this.isVerified = isVerified;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
