package com.bookflow.storage.dto;

public record UploadResponse(
        String imageUrl,
        String publicID
) {
}
