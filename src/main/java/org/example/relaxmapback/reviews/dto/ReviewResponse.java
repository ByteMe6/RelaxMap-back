package org.example.relaxmapback.reviews.dto;

import org.example.relaxmapback.places.dto.PlaceResponse;
import org.example.relaxmapback.users.dto.UserResponse;

public record ReviewResponse(long id, String text, int rating, UserResponse userResponse, PlaceResponse placeResponse) {
}
