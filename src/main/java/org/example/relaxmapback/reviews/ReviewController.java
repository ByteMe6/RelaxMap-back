package org.example.relaxmapback.reviews;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.reviews.dto.ReviewRequest;
import org.example.relaxmapback.reviews.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping("/all")
  public ResponseEntity<List<ReviewResponse>> getAllReviews() {
    return ResponseEntity.ok(reviewService.getAllReviews());
  }

  @GetMapping("/for-place/{id}")
  public ResponseEntity<List<ReviewResponse>> getReviewsForPlace(@PathVariable Long id) {
    return ResponseEntity.ok(reviewService.getReviewsForPlace(id));
  }

  @PostMapping
  public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest request, Authentication auth) {
    return ResponseEntity.ok(reviewService.createReview(request, auth.getName()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id, Authentication auth) {
    reviewService.deleteReview(id, auth.getName());

    return ResponseEntity.noContent().build();
  }
}
