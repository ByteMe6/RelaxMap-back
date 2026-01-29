package org.example.relaxmapback.reviews;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.common.PageResponse;
import org.example.relaxmapback.reviews.dto.ReviewRequest;
import org.example.relaxmapback.reviews.dto.ReviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping("/all")
  public ResponseEntity<PageResponse<ReviewResponse>> getAllReviews(Pageable pageable) {
    return ResponseEntity.ok(reviewService.getAllReviews(pageable));
  }

  @GetMapping("/for-place/{id}")
  public ResponseEntity<PageResponse<ReviewResponse>> getReviewsForPlace(@PathVariable Long id, Pageable pageable) {
    return ResponseEntity.ok(reviewService.getReviewsForPlace(id, pageable));
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
