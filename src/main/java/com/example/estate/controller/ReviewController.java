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

@RestController
public class ReviewController {
  private final ReviewRepository reviewRepository;

  public ReviewController(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @GetMapping("/reviews")
  ResponseEntity<Response<List<Review>>> getAllReviews() {
    Response<List<Review>> review = new Response<List<Review>>(true, reviewRepository.findAll());
    return ResponseEntity.ok().body(review);
  }
  @PostMapping("/reviews/new")
  ResponseEntity<Response<Review>> createReview(@RequestBody Review review) {
    Review newReview = review;
    reviewRepository.save(newReview);
    Response<Review> savedReview = new Response<Review>(true, newReview);
    return ResponseEntity.ok().body(savedReview);
  }
  @DeleteMapping("/review/{id}")
  ResponseEntity<Response<String>> deleteReview(@PathVariable String id) {
    Response<String> newResponse = new Response<String>(true, "Successfully deleted review");
    return ResponseEntity.ok().body(newResponse);

  }
  
}
