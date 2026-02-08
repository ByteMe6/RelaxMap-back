package org.example.relaxmapback.reviews;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.common.PageResponse;
import org.example.relaxmapback.exceptions.access.AccessDeniedException;
import org.example.relaxmapback.exceptions.places.PlaceNotExistsException;
import org.example.relaxmapback.exceptions.reviews.ReviewNotExistsException;
import org.example.relaxmapback.exceptions.users.UserNotExistsException;
import org.example.relaxmapback.places.Place;
import org.example.relaxmapback.places.PlaceRepository;
import org.example.relaxmapback.places.dto.PlaceResponse;
import org.example.relaxmapback.reviews.dto.ReviewRequest;
import org.example.relaxmapback.reviews.dto.ReviewResponse;
import org.example.relaxmapback.users.User;
import org.example.relaxmapback.users.UserRepository;
import org.example.relaxmapback.users.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;

  public PageResponse<ReviewResponse> getAllReviews(Pageable pageable) {
    Page<Review> reviews = reviewRepository.findAll(pageable);

    return this.toPageResponse(reviews);
  }

  public PageResponse<ReviewResponse> getReviewsForPlace(Long id, Pageable pageable) {
    Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotExistsException("Place is not exists"));
    Page<Review> reviews = reviewRepository.findByPlace(place, pageable);

    return this.toPageResponse(reviews);
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

    return this.toResponse(review);
  }

  public void deleteReview(Long id, String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));
    Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotExistsException("Review is not exists"));

    if (!review.getUser().getId().equals(user.getId())) {
      throw new AccessDeniedException("You cannot delete someone someone else's review");
    }

    reviewRepository.delete(review);
  }

  private PageResponse<ReviewResponse> toPageResponse(Page<Review> page) {
    return new PageResponse<>(
            page.getContent().stream().map(this::toResponse).toList(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.getNumber(),
            page.getSize()
    );
  }

  private ReviewResponse toResponse(Review review) {
    return new ReviewResponse(
            review.getId(),
            review.getText(),
            review.getRating(),
            new UserResponse(
                    review.getUser().getId(),
                    review.getUser().getName(),
                    review.getUser().getEmail()
            ),
            new PlaceResponse(
                    review.getPlace().getId(),
                    review.getPlace().getName(),
                    review.getPlace().getPlaceType(),
                    review.getPlace().getRegion(),
                    review.getPlace().getImageName(),
                    review.getPlace().getImageName(),
                    new UserResponse(review.getUser().getId(), review.getUser().getName(), review.getUser().getEmail())
            )
    );
  }
}
