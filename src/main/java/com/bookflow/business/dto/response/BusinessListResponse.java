package com.bookflow.business.dto.response;

import com.bookflow.business.enums.BusinessStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BusinessListResponse(
        Long id,
        String name,
        String description,
        String address,
        String city
) {
}
