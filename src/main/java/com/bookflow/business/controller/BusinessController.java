package com.bookflow.business.controller;

import com.bookflow.auth.security.UserPrincipal;
import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessDetailsResponse;
import com.bookflow.business.dto.response.BusinessListResponse;
import com.bookflow.business.dto.response.BusinessResponseAdmin;
import com.bookflow.business.service.concrete.BusinessServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessServiceHandler serviceHandler;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public BusinessResponseAdmin createBusiness(@RequestBody CreateBusinessRequest request,
                                                @AuthenticationPrincipal UserPrincipal principal
    ) {
        return serviceHandler.createBusiness(request, principal.user().getId());
    }

    @GetMapping("/my-business")
    @ResponseStatus(HttpStatus.OK)
    public BusinessDetailsResponse getMyBusiness(
            @AuthenticationPrincipal UserPrincipal principal) {

        return serviceHandler.getMyBusiness(principal.user().getId());
    }

    @GetMapping("/all-business")
    @ResponseStatus(HttpStatus.OK)
    public List<BusinessListResponse> getAllBusiness() {
       return serviceHandler.getAllActiveBusiness();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessDetailsResponse getBusinessById(@PathVariable Long id) {
        return serviceHandler.getBusinessById(id);
    }

    @DeleteMapping("/delete-me")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBusiness(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        serviceHandler.deleteBusiness(userPrincipal.user().getId());
    }
}
