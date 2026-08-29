package com.bookflow.user.service.concrete;

import com.bookflow.exception.UserNotFoundException;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.repository.UserRepository;
import com.bookflow.user.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User does not exist with this email: " + email));
    }

    @Override
    public UserEntity updateUser(Long id, String firstName, String lastName, String email, String password) {
        return null;
    }
}
