package org.example.relaxmapback.reviews;

import org.example.relaxmapback.places.Place;
import org.example.relaxmapback.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByUser(User user);
  Page<Review> findByPlace(Place place, Pageable pageable);
}
