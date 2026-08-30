package com.bookflow.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email cannot be empty.")
        @Email(regexp = "^[\\w.-]+@[\\w.-]+\\.(com|ru)$", message = "The email must also have the @ symbol and end with .com or .ru.")
        String email,

        @NotBlank(message = "Password cannot be empty.")
        @Size(min = 6, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Role is required")
        String role
) {
}
