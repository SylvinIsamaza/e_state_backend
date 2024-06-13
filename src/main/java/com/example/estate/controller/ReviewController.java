package com.example.estate.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.Review;
import com.example.estate.repository.ReviewRepository;
import com.example.estate.utils.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@Tag(name="Review", description = "Review APIs")
public class ReviewController {
  private final ReviewRepository reviewRepository;

  public ReviewController(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Operation(summary = "Get all reviews", 
             description = "Retrieve a list of all reviews",
             responses = {
               @ApiResponse(description = "Successful operation", 
                            responseCode = "200", 
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Review.class)))
             })
  @GetMapping("/reviews")
  ResponseEntity<Response<List<Review>>> getAllReviews() {
    Response<List<Review>> review = new Response<List<Review>>(true, reviewRepository.findAll());
    return ResponseEntity.ok().body(review);
  }

  @Operation(summary = "Create a new review", 
             description = "Add a new review to the database",
             responses = {
               @ApiResponse(description = "Successful operation", 
                            responseCode = "200", 
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Review.class)))
             })
  @PostMapping("/reviews/new")
  ResponseEntity<Response<Review>> createReview(@RequestBody Review review) {
    Review newReview = review;
    reviewRepository.save(newReview);
    Response<Review> savedReview = new Response<Review>(true, newReview);
    return ResponseEntity.ok().body(savedReview);
  }

  @Operation(summary = "Delete a review", 
             description = "Delete a review by its ID",
             responses = {
               @ApiResponse(description = "Successful operation", 
                            responseCode = "200", 
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
             })
  @DeleteMapping("/review/{id}")
  ResponseEntity<Response<String>> deleteReview(@PathVariable String id) {
    // Assuming you need to add the deletion logic here
    reviewRepository.deleteById(id);
    Response<String> newResponse = new Response<String>(true, "Successfully deleted review");
    return ResponseEntity.ok().body(newResponse);
  }
}
