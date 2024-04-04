package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.Property;
import com.example.estate.services.PropertyRepository;



@RestController
public class PropertyController {
    final private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/property")
    List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }
    @PostMapping("/property/new")
    Property addProperty(@RequestBody Property property){
        Property newProperty=property;
        propertyRepository.save(newProperty);
        return newProperty;
    }
    @DeleteMapping("/property/{id}")
    void DeletePropertyById( @PathVariable String id){
        propertyRepository.deleteById(id);
    }
    @DeleteMapping("/property")
    void DeleteAll(@PathVariable String id){
        propertyRepository.deleteAll();
    }
    @GetMapping("property/{id}")
    Optional<Property> getPropertyById( @PathVariable String id){
        return propertyRepository.findById(id);
        }
    
    
    

}
