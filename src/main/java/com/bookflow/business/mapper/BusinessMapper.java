package com.bookflow.business.mapper;

import com.bookflow.business.dto.response.BusinessResponse;
import com.bookflow.business.entity.BusinessEntity;

public class BusinessMapper {
    public BusinessResponse toResponse(BusinessEntity business) {
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
                business.getCreatedAt(),
                business.getUpdatedAt()
        );
    }
}
