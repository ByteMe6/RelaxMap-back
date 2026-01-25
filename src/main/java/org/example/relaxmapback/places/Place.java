package org.example.relaxmapback.places;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.relaxmapback.users.User;

@Entity
@Table(name = "places")
@Getter
@Setter
public class Place {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String placeType;

  @Column(nullable = false)
  private String region;

  @Column(nullable = false)
  private String imageName;

  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
