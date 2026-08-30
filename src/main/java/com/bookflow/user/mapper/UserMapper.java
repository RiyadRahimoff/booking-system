package com.bookflow.user.mapper;

import com.bookflow.user.dto.response.UserResponse;
import com.bookflow.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(UserEntity user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getStatus(),
                user.getEmail()
        );
    }
}
