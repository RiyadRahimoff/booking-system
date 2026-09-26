package com.bookflow.business.dto.response;

import java.math.BigDecimal;

public record BusinessDetailsResponse(
        Long id,
        String name,
        String description,
        String phone,
        String email,
        String address,
        String city,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
