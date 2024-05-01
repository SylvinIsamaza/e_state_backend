package com.example.estate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private int price;
    private String location;
    private int area;
    private int nbrOfBeds;
    private boolean kitchen;
    private boolean wifi;
    private boolean baths;
    private boolean parkingArea;
    private boolean balcony;
    private String status;
    @ManyToOne
    Agent agent;

    public Property(String name, String description, int price, String location, int area, int nbrOfBeds,
            boolean kitchen, boolean wifi, boolean parkingArea,boolean baths, boolean balcony, String status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.location = location;
        this.area = area;
        this.nbrOfBeds = nbrOfBeds;
        this.kitchen = kitchen;
        this.wifi = wifi;
        this.parkingArea = parkingArea;
        this.balcony = balcony;
        this.status = status;
    }

}
