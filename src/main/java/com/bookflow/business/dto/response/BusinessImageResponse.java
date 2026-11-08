package com.bookflow.business.dto.response;

import com.bookflow.business.enums.BusinessImageType;

public record BusinessImageResponse(
        Long id,
        String imageUrl,
        BusinessImageType type,
        Integer sortOrder
) {
}
