package com.bookflow.auth.dto.request;

public record ResetPasswordRequest(
        String email,
        String code,
        String newPassword
) {
}
