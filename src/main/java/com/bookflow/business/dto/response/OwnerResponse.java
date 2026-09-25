package com.bookflow.business.dto.response;

public record OwnerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String role,
        String status
) {
}
