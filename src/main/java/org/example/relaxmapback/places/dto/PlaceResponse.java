package org.example.relaxmapback.places.dto;

import org.example.relaxmapback.users.dto.UserResponse;

public record PlaceResponse(long id, String name, String placeType, String region, String description, String imageName, UserResponse userResponse) {
}
