package com.bookflow.business.service.abstraction;

import com.bookflow.business.dto.response.BusinessImageResponse;
import com.bookflow.business.enums.BusinessImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BusinessImageService {
    BusinessImageResponse uploadImage(
            Long businessId,
            MultipartFile file,
            BusinessImageType type,
            Long ownerId
    );

    void setCover(
            Long businessId,
            Long imageId,
            Long ownerId
    );

    void deleteImage(
            Long businessId,
            Long imageId,
            Long ownerId
    );

    List<BusinessImageResponse> getImages(Long businessId);
}
