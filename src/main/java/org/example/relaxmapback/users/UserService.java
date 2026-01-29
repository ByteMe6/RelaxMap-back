package org.example.relaxmapback.users;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.exceptions.users.UserNotExistsException;
import org.example.relaxmapback.places.Place;
import org.example.relaxmapback.places.PlaceRepository;
import org.example.relaxmapback.reviews.Review;
import org.example.relaxmapback.reviews.ReviewRepository;
import org.example.relaxmapback.users.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;
  private final ReviewRepository reviewRepository;

  public UserResponse getUserByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }

  public UserResponse getUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotExistsException("User is not exists"));

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }

  public void deleteUser(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));
    List<Place> places = placeRepository.findByUser(user);
    List<Review> reviews = reviewRepository.findByUser(user);

    reviewRepository.deleteAll(reviews);
    placeRepository.deleteAll(places);
    userRepository.delete(user);
  }
}
