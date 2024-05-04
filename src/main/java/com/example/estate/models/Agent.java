package com.example.estate.models;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne()
    @JoinColumn(name = "user_id")
    User user;
    private String about;
    private String phoneNumber;
    private String location;
    private String facebook;
    private String instagram;
    private String twitter;
    private String serviceArea;
    private String taxNumber;
    private String agentLicence;
    private boolean isVerified;
    @OneToMany(mappedBy = "agent")
    List <Property> property;

    public Agent(String about, String phoneNumber, String location,
    
            String facebook, String instagram, String twitter, String serviceArea, String taxNumber,
            String agentLicence,
            boolean isVerified) {
        this.about = about;
        this.phoneNumber = phoneNumber;
        this.location = location;
        
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.serviceArea = serviceArea;
        this.taxNumber = taxNumber;
        this.agentLicence = agentLicence;
        this.isVerified = isVerified;
    }

}
