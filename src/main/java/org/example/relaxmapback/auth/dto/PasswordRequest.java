package org.example.relaxmapback.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRequest(
        @NotBlank(message = "Old password is required")
        @Size(min = 8, max = 128, message = "Password must be at least 8 characters and no more than 128 characters")
        String oldPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 8, max = 128, message = "Password must be at least 8 characters and no more than 128 characters")
        String newPassword
) {
}
