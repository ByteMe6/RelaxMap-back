package org.example.relaxmapback.places;

import org.example.relaxmapback.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
  Page<Place> findByUser(User user, Pageable pageable);
  List<Place> findByUser(User user);
}
