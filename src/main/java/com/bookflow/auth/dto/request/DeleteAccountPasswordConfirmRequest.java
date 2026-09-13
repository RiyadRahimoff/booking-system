package com.bookflow.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountPasswordConfirmRequest(
        @NotBlank(message = "Password cannot be empty.")
        String password
) {
}
