package org.example.relaxmapback.reviews;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.exceptions.access.AccessDeniedException;
import org.example.relaxmapback.exceptions.places.PlaceNotExistsException;
import org.example.relaxmapback.exceptions.reviews.ReviewNotExistsException;
import org.example.relaxmapback.exceptions.users.UserNotExistsException;
import org.example.relaxmapback.places.Place;
import org.example.relaxmapback.places.PlaceRepository;
import org.example.relaxmapback.reviews.dto.ReviewRequest;
import org.example.relaxmapback.reviews.dto.ReviewResponse;
import org.example.relaxmapback.users.User;
import org.example.relaxmapback.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;

  public List<ReviewResponse> getAllReviews() {
    List<Review> reviews = reviewRepository.findAll();

    return reviews.stream()
            .map(review -> new ReviewResponse(
                    review.getId(),
                    review.getText(),
                    review.getRating(),
                    review.getUser().getId(),
                    review.getPlace().getId()
            ))
            .toList();
  }

  public List<ReviewResponse> getReviewsForPlace(Long id) {
    Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotExistsException("Place is not exists"));
    List<Review> reviews = reviewRepository.findByPlace(place);

    return reviews.stream()
            .map(review -> new ReviewResponse(
                    review.getId(),
                    review.getText(),
                    review.getRating(),
                    review.getUser().getId(),
                    review.getPlace().getId()
            ))
            .toList();
  }

  public ReviewResponse createReview(ReviewRequest request, String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));
    Place place = placeRepository.findById(request.placeId()).orElseThrow(() -> new PlaceNotExistsException("Place is not exists"));

    Review review = new Review();

    review.setText(request.text());
    review.setRating(request.rating());
    review.setUser(user);
    review.setPlace(place);

    reviewRepository.save(review);

    return new ReviewResponse(
            review.getId(),
            review.getText(),
            review.getRating(),
            review.getUser().getId(),
            review.getPlace().getId()
    );
  }

  public ReviewResponse deleteReview(Long id, String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));
    Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotExistsException("Review is not exists"));

    if (!review.getUser().getId().equals(user.getId())) {
      throw new AccessDeniedException("You cannot delete someone someone else's review");
    }

    reviewRepository.delete(review);

    return new ReviewResponse(
            review.getId(),
            review.getText(),
            review.getRating(),
            review.getUser().getId(),
            review.getPlace().getId()
    );
  }
}
