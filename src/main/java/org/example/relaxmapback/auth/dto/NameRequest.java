package org.example.relaxmapback.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NameRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 32, message = "Name must be at least 2 characters and no more than 32 characters")
        String newName
) {
}
