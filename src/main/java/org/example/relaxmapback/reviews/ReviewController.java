package org.example.relaxmapback.reviews;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.reviews.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping
  public ResponseEntity<List<ReviewResponse>> getAllReviews() {
    return ResponseEntity.ok(reviewService.getAllReviews());
  }


}
