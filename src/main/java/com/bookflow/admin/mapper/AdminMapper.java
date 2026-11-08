package com.bookflow.admin.mapper;

import com.bookflow.admin.dto.response.AdminUsersResponse;
import com.bookflow.user.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public AdminUsersResponse toResponse(UserEntity user){
        return new AdminUsersResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
