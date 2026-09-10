package com.bookflow.admin.controller;

import com.bookflow.admin.services.concrete.AdminServiceHandler;
import com.bookflow.user.dto.response.UserResponse;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.service.concrete.UserServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceHandler userServiceHandler;
    private final AdminServiceHandler adminServiceHandler;

    @GetMapping("/find/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userServiceHandler.getUserByEmail(email);
    }

    @GetMapping("/get/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getUsers() {
        return adminServiceHandler.getUsers();
    }
}

