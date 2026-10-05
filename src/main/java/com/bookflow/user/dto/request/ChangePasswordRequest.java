package com.bookflow.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        String oldPassword,
        @NotBlank(message = "Password cannot be empty.")
        @Size(min = 6, message = "Password must be at least 8 characters")
        String newPassword
) {
}
