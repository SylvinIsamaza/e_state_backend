package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.Property;
import com.example.estate.repository.PropertyRepository;
import com.example.estate.utils.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@Tag(name="Property", description = "Property APIs")
public class PropertyController {
    final private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Operation(summary = "Get all properties", 
               description = "Retrieve a list of all properties",
               responses = {
                 @ApiResponse(description = "Successful operation", 
                              responseCode = "200", 
                              content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = Property.class)))
               })
    @GetMapping("/property")
    List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    @Operation(summary = "Add a new property", 
               description = "Add a new property to the database",
               responses = {
                 @ApiResponse(description = "Successful operation", 
                              responseCode = "200", 
                              content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = Property.class)))
               })
    @PostMapping("/property/new")
    Property addProperty(@RequestBody Property property) {
        Property newProperty = property;
        propertyRepository.save(newProperty);
        return newProperty;
    }

    @Operation(summary = "Delete a property by ID", 
               description = "Delete a property by its ID",
               responses = {
                 @ApiResponse(description = "Successful operation", 
                              responseCode = "200", 
                              content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = String.class)))
               })
    @DeleteMapping("/property/{id}")
    void deletePropertyById(@PathVariable String id) {
        propertyRepository.deleteById(id);
    }

    @Operation(summary = "Delete all properties", 
               description = "Delete all properties",
               responses = {
                 @ApiResponse(description = "Successful operation", 
                              responseCode = "200", 
                              content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = String.class)))
               })
    @DeleteMapping("/property")
    void deleteAll() {
        propertyRepository.deleteAll();
    }

    @Operation(summary = "Get a property by ID", 
               description = "Retrieve a property by its ID",
               responses = {
                 @ApiResponse(description = "Successful operation", 
                              responseCode = "200", 
                              content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = Property.class)))
               })
    @GetMapping("/property/{id}")
    Optional<Property> getPropertyById(@PathVariable String id) {
        return propertyRepository.findById(id);
    }

    @Operation(summary = "Update a property by ID", 
               description = "Update a property by its ID",
               responses = {
                 @ApiResponse(description = "Successful operation", 
                              responseCode = "200", 
                              content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = Property.class)))
               })
    @PostMapping("/property/{id}")
    ResponseEntity<Response<Property>> updateProperty(@RequestBody Property property, @PathVariable String id) {
        // Implement the update logic
        if (propertyRepository.existsById(id)) {
            property.setId(id); // Assuming Property has a setId method
            Property updatedProperty = propertyRepository.save(property);
            Response<Property> response = new Response<Property>(true, updatedProperty);
            return ResponseEntity.ok().body(response);
        } else {
            Response<Property> response = new Response<Property>(false, null, "Property not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
