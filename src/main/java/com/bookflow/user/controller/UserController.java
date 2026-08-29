package com.bookflow.user.controller;

import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.service.concrete.UserServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceHandler userServiceHandler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUserById(@PathVariable Long id) {
        return userServiceHandler.getUserById(id);
    }

    @GetMapping("find/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUserByEmail(@PathVariable String email) {
        return userServiceHandler.getUserByEmail(email);
    }
}
