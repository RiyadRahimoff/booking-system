package com.bookflow.business.dto.response;

import com.bookflow.business.enums.BusinessStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BusinessResponseAdmin(
        Long id,
        String name,
        String description,
        String phone,
        String email,
        String address,
        String city,
        BigDecimal latitude,
        BigDecimal longitude,
        BusinessStatus status,
        OwnerResponse ownerResponse,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
