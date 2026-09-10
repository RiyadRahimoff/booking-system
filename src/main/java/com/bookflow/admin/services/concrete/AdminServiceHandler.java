package com.bookflow.admin.services.concrete;

import com.bookflow.admin.repository.AdminRepository;
import com.bookflow.admin.services.abstraction.AdminService;
import com.bookflow.exception.UserNotFoundException;
import com.bookflow.user.entity.UserEntity;
import liquibase.license.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceHandler implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<UserEntity> getUsers() {
        List<UserEntity> users = adminRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Users not found");
        }

        return users;
    }
}
