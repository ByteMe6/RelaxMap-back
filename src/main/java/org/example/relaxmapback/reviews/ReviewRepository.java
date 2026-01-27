package org.example.relaxmapback.reviews;

import org.example.relaxmapback.places.Place;
import org.example.relaxmapback.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  Review findByUser(User user);
  Review findByPlace(Place place);
}
