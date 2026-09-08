package com.bookflow.user.controller;

import com.bookflow.auth.security.UserPrincipal;
import com.bookflow.user.dto.request.UpdateUserRequest;
import com.bookflow.user.dto.response.UserResponse;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.service.concrete.UserServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceHandler userServiceHandler;


    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@AuthenticationPrincipal UserPrincipal principal) {
        return userServiceHandler.getUserById(principal.user().getId());
    }


    @PatchMapping("/me/update")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UpdateUserRequest updateUser) {
       return userServiceHandler.updateUser(principal.user().getId(), updateUser);
    }

    @PatchMapping("/me/deactivate")
    public UserResponse deactivateUser(@AuthenticationPrincipal UserPrincipal principal) {
        return userServiceHandler.deactivateUser(principal.user().getId());
    }


}
