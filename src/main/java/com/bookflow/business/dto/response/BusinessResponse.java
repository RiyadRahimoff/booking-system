package com.bookflow.business.dto.response;

import com.bookflow.business.enums.BusinessStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BusinessResponse(
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
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
