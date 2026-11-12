package com.bookflow.admin.service.abstraction;

import com.bookflow.admin.dto.response.AdminUsersResponse;
import com.bookflow.user.entity.UserEntity;

import java.util.List;

public interface AdminService {
    List<UserEntity> getUsers();

    AdminUsersResponse getUserByID(Long id);

    void blockUser (String blockMessage, Long id);

    void unblockUser (Long id);

}
