
package com.bookflow.business.controller;

import com.bookflow.auth.security.UserPrincipal;
import com.bookflow.business.dto.response.BusinessImageResponse;
import com.bookflow.business.enums.BusinessImageType;
import com.bookflow.business.service.concrete.BusinessImageServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/business/{businessId}/images")
@RequiredArgsConstructor
public class BusinessImageController {

    private final BusinessImageServiceHandler businessImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessImageResponse uploadImage(
            @PathVariable Long businessId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") BusinessImageType type,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return businessImageService.uploadImage(
                businessId,
                file,
                type,
                getCurrentUserId(principal)
        );
    }

    @GetMapping
    public List<BusinessImageResponse> getImages(
            @PathVariable Long businessId
    ) {
        return businessImageService.getImages(businessId);
    }

    @PatchMapping("/{imageId}/cover")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setCover(
            @PathVariable Long businessId,
            @PathVariable Long imageId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        businessImageService.setCover(
                businessId,
                imageId,
                getCurrentUserId(principal)
        );
    }

    @DeleteMapping("/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(
            @PathVariable Long businessId,
            @PathVariable Long imageId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        businessImageService.deleteImage(
                businessId,
                imageId,
                getCurrentUserId(principal)
        );
    }

    private Long getCurrentUserId(UserPrincipal principal) {
        if (principal == null || principal.user() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }

        return principal.user().getId();
    }
}
