package com.bookflow.user.service.concrete;

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
      return  userRepository.findById(id).orElseThrow();

    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserEntity updateUser(Long id, String firstName, String lastName, String email, String password) {
        return null;
    }
}
