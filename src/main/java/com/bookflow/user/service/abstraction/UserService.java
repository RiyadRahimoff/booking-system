package com.bookflow.user.service.abstraction;

import com.bookflow.user.entity.UserEntity;
import liquibase.license.User;

public interface UserService {
    UserEntity getUserById(Long id);

    UserEntity getUserByEmail(String email);

    UserEntity updateUser(Long id, String firstName, String lastName, String email, String password);
}
