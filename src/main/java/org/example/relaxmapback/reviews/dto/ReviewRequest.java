package org.example.relaxmapback.reviews.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewRequest(
        @NotBlank
        @Size(min = 20, max = 1000, message = "Name must be at least 20 characters and no more than 1000 characters")
        String text,

        @Min(value = 1, message = "Rating cannot be less than 1")
        @Max(value = 5, message = "Rating cannot be greater than 5")
        int rating,

        long placeId
) {
}
