package com.bookflow.user.controller;

import com.bookflow.user.dto.request.UpdateUserRequest;
import com.bookflow.user.dto.response.UserResponse;
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
    public UserResponse getUserById(@PathVariable Long id) {
        return userServiceHandler.getUserById(id);
    }

    @GetMapping("/find/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userServiceHandler.getUserByEmail(email);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUser) {
       return userServiceHandler.updateUser(id,updateUser);
    }
}
