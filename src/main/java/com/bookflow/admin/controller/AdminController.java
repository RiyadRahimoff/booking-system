package com.bookflow.admin.controller;

import com.bookflow.admin.service.concrete.AdminServiceHandler;
import com.bookflow.business.dto.response.BusinessResponse;
import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.service.concrete.BusinessServiceHandler;
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
    private final BusinessServiceHandler businessServiceHandler;

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

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUserByID(@PathVariable Long id) {
        return adminServiceHandler.getUserByID(id);
    }

    @GetMapping("/all/business")
    @ResponseStatus(HttpStatus.OK)
    public List<BusinessResponse> getAllBusiness() {
      return  businessServiceHandler.getAllBusiness();
    }

    @GetMapping("/business/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse getBusinessById(@PathVariable Long id) {
         return businessServiceHandler.getBusinessById(id);
    }

    @GetMapping("/pending-business")
    @ResponseStatus(HttpStatus.OK)
    public List<BusinessResponse> getAllPendingBusiness() {
        return businessServiceHandler.getAllPendingBusiness();
    }

    @PostMapping("/business/approve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse approvePendingBusiness(@PathVariable Long id) {
        return businessServiceHandler.approvePendingBusiness(id);
    }

}

