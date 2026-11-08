package com.bookflow.admin.service.concrete;

import com.bookflow.admin.dto.response.AdminUsersResponse;
import com.bookflow.admin.mapper.AdminMapper;
import com.bookflow.admin.repository.AdminRepository;
import com.bookflow.admin.service.abstraction.AdminService;
import com.bookflow.exception.UserNotFoundException;
import com.bookflow.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceHandler implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Override
    public List<UserEntity> getUsers() {
        List<UserEntity> users = adminRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Users not found");
        }

        return users;
    }

    @Override
    public AdminUsersResponse getUserByID(Long id) {
        UserEntity userEntity = adminRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found !?"));
        return adminMapper.toResponse(userEntity);
    }
}
