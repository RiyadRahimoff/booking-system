package com.bookflow.business.controller;

import com.bookflow.auth.security.UserPrincipal;
import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessResponse;
import com.bookflow.business.service.concrete.BusinessServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessServiceHandler serviceHandler;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public BusinessResponse createBusiness(@RequestBody CreateBusinessRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return serviceHandler.createBusiness(request, principal.user().getId());
    }

    @GetMapping("/my-business")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse getBusinessById(@AuthenticationPrincipal UserPrincipal principal) {
        return serviceHandler.getBusinessById(principal.user().getId());
    }
}
