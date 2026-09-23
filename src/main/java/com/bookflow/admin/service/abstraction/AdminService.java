package com.bookflow.admin.service.abstraction;

import com.bookflow.user.entity.UserEntity;

import java.util.List;

public interface AdminService {
    List<UserEntity> getUsers();

    UserEntity getUserByID(Long id);
}
