package org.example.relaxmapback.reviews;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.relaxmapback.places.Place;
import org.example.relaxmapback.users.User;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  private Integer rating;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "place_id")
  private Place place;
}
