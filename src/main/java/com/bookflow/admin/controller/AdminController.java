package com.bookflow.admin.controller;

import com.bookflow.user.dto.response.UserResponse;
import com.bookflow.user.service.concrete.UserServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceHandler userServiceHandler;

    @GetMapping("/find/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userServiceHandler.getUserByEmail(email);
    }
}
