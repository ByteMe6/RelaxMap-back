package org.example.relaxmapback.reviews;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.reviews.dto.ReviewResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public List<ReviewResponse> getAllReviews() {
    List<Review> reviews = reviewRepository.findAll();

    return reviews.stream()
            .map(review -> new ReviewResponse(
                    review.getId(),
                    review.getText(),
                    review.getRating(),
                    review.getUser().getId()))
            .toList();
  }

//  public Review getById(Long id) {
//    reviewRepository.findById(id);
//  }
}
