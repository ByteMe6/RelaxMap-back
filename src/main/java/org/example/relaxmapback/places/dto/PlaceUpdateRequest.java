package org.example.relaxmapback.places.dto;

import jakarta.validation.constraints.Size;

public record PlaceUpdateRequest(
        @Size(min = 3, max = 96, message = "Name must be at least 3 characters and no more than 96 characters")
        String name,

        @Size(min = 3, max = 96, message = "Place type must be at least 3 characters and no more than 96 characters")
        String placeType,

        @Size(min = 3, max = 96, message = "Region must be at least 3 characters and no more than 96 characters")
        String region,

        @Size(min = 20, max = 2000, message = "Description must be at least 20 characters and no more than 2000 characters")
        String description
) {
}
