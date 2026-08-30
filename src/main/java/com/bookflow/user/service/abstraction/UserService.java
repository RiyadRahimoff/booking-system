package com.bookflow.user.service.abstraction;

import com.bookflow.user.dto.request.UpdateUserRequest;
import com.bookflow.user.dto.response.UserResponse;
import com.bookflow.user.entity.UserEntity;

public interface UserService {
    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    UserResponse updateUser(Long id, UpdateUserRequest updateUser);
}
