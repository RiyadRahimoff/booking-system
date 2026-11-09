package com.bookflow.admin.service.concrete;

import com.bookflow.admin.dto.response.AdminUsersResponse;
import com.bookflow.admin.mapper.AdminMapper;
import com.bookflow.admin.repository.AdminRepository;
import com.bookflow.admin.service.abstraction.AdminService;
import com.bookflow.email.abstraction.EmailProducer;
import com.bookflow.email.entity.EmailBlockMessage;
import com.bookflow.exception.UserNotFoundException;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceHandler implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final EmailProducer emailProducer;

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

    @Override
    public void blockUser(String blockMessage, Long id) {
        UserEntity user = adminRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User doesn't exist!"));
        user.setStatus(StatusEnum.BLOCKED);
        emailProducer.sendBlockUserEmailMessage(new EmailBlockMessage(user.getEmail(),blockMessage));
        adminRepository.save(user);
    }
}
