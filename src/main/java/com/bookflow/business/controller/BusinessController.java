package com.bookflow.business.controller;

import com.bookflow.auth.security.UserPrincipal;
import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessDetailsResponse;
import com.bookflow.business.dto.response.BusinessListResponse;
import com.bookflow.business.dto.response.BusinessResponseAdmin;
import com.bookflow.business.service.concrete.BusinessServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<BusinessListResponse>> getAllActiveBusiness(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                serviceHandler.getAllActiveBusiness(pageable)
        );
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
