package com.bookflow.admin.dto.response;

import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;

import java.time.LocalDateTime;

public record AdminUsersResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserEnum role,
        StatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
