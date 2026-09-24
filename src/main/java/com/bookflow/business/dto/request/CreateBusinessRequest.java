package com.bookflow.business.dto.request;

import java.math.BigDecimal;

public record CreateBusinessRequest(
        String name,
        String description,
        String email,
        String city,
        String phone,
        String address,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
