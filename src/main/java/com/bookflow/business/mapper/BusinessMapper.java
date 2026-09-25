package com.bookflow.business.mapper;

import com.bookflow.business.dto.response.BusinessResponse;
import com.bookflow.business.dto.response.OwnerResponse;
import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {
    public BusinessResponse toResponse(BusinessEntity business) {
        UserEntity owner = business.getOwner();

        OwnerResponse ownerResponse = new OwnerResponse(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getRole().name(),
                owner.getStatus().name()
        );

        return new BusinessResponse(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getPhone(),
                business.getEmail(),
                business.getAddress(),
                business.getCity(),
                business.getLatitude(),
                business.getLongitude(),
                business.getStatus(),
                ownerResponse,
                business.getCreatedAt(),
                business.getUpdatedAt()
        );
    }
}
