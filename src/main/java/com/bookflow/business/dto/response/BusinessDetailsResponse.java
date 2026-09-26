package com.bookflow.business.dto.response;

import com.bookflow.business.enums.BusinessStatus;

import java.math.BigDecimal;

public record BusinessDetailsResponse(
        Long id,
        String name,
        String description,
        BusinessStatus status,
        String phone,
        String email,
        String address,
        String city,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
