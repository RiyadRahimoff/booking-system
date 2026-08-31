package com.bookflow.user.service.concrete;

import com.bookflow.exception.InvalidRoleException;
import com.bookflow.exception.UserNotFoundException;
import com.bookflow.exception.UserStatusPendingException;
import com.bookflow.user.dto.request.UpdateUserRequest;
import com.bookflow.user.dto.response.UserResponse;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;
import com.bookflow.user.mapper.UserMapper;
import com.bookflow.user.repository.UserRepository;
import com.bookflow.user.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (user.getStatus() == StatusEnum.PENDING || (user.getStatus() == StatusEnum.BLOCKED)) {
            throw new UserStatusPendingException("User status is PENDING or BLOCKED and profile cannot show");
        }
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User does not exist with this email: " + email));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest updateUser) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getStatus() == StatusEnum.PENDING || (user.getStatus() == StatusEnum.BLOCKED)) {
            throw new UserStatusPendingException("User status is PENDING or BLOCKED and profile cannot update");
        }
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);

    }

    @Override
    public UserResponse deactivateUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole() == UserEnum.ADMIN) {
            throw new InvalidRoleException("This role cannot be frozen");
        } else if (user.getStatus() == StatusEnum.BLOCKED || user.getStatus() == StatusEnum.PENDING || user.getStatus() == StatusEnum.INACTIVE) {
            throw new InvalidRoleException("Your account status don't allow this operation");
        }
        user.setStatus(StatusEnum.INACTIVE);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
