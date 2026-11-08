package com.bookflow.business.dto.response;

public record BusinessListResponse(
        Long id,
        String name,
        String description,
        String address,
        String city,
        String coverUrl,
        String logoUrl
) {
}
