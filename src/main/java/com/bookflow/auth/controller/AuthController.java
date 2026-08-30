package com.bookflow.auth.controller;

import com.bookflow.auth.dto.request.RegisterRequest;
import com.bookflow.auth.service.concrete.AuthServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceHandler authServiceHandler;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterRequest request) {
        authServiceHandler.registerUser(request);
    }
}
