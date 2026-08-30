package com.bookflow.user.dto.response;

import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;

public record UserResponse(
        String firstName,
        String lastName,
        UserEnum userEnum,
        StatusEnum statusEnum,
        String email
) {
}
