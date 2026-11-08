package com.bookflow.business.mapper;

import com.bookflow.business.dto.response.BusinessDetailsResponse;
import com.bookflow.business.dto.response.BusinessListResponse;
import com.bookflow.business.dto.response.BusinessResponseAdmin;
import com.bookflow.business.dto.response.OwnerResponse;
import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.entity.BusinessImageEntity;
import com.bookflow.business.enums.BusinessImageType;
import com.bookflow.business.repository.BusinessImageRepository;
import com.bookflow.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessMapper {
    private final BusinessImageRepository businessImageRepository;
    public BusinessResponseAdmin toResponseAdmin(BusinessEntity business) {
        UserEntity owner = business.getOwner();

        OwnerResponse ownerResponse = new OwnerResponse(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getRole().name(),
                owner.getStatus().name()
        );

        return new BusinessResponseAdmin(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getPhone(),
                business.getEmail(),
                business.getAddress(),
                business.getCity().toString(),
                business.getLatitude(),
                business.getLongitude(),
                business.getStatus(),
                ownerResponse,
                business.getCreatedAt(),
                business.getUpdatedAt()
        );
    }

    public BusinessDetailsResponse toDetailsResponse(BusinessEntity business) {
        return new BusinessDetailsResponse(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getStatus(),
                business.getPhone(),
                business.getEmail(),
                business.getAddress(),
                business.getCity().toString(),
                business.getLatitude(),
                business.getLongitude()
        );
    }


    public BusinessListResponse toResponse(BusinessEntity business) {

        Long businessId = business.getId();

        String coverUrl = businessImageRepository
                .findByBusiness_IdAndType(
                        businessId,
                        BusinessImageType.COVER
                )
                .map(BusinessImageEntity::getImageUrl)
                .orElse(null);

        String logoUrl = businessImageRepository
                .findByBusiness_IdAndType(
                        businessId,
                        BusinessImageType.LOGO
                )
                .map(BusinessImageEntity::getImageUrl)
                .orElse(null);

        return new BusinessListResponse(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getAddress(),
                business.getCity().toString(),
                coverUrl,
                logoUrl
        );
    }
}
