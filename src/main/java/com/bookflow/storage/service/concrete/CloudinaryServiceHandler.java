package com.bookflow.storage.service.concrete;

import com.bookflow.storage.dto.UploadResponse;
import com.bookflow.storage.service.abstraction.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class CloudinaryServiceHandler implements CloudinaryService {

private final Cloudinary cloudinary;
    @Override
    public UploadResponse upload(MultipartFile file, String folder) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            return new UploadResponse(
                    (String) result.get("secure_url"),
                    (String) result.get("public_id")
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Image upload failed", e
            );
        }
    }

    @Override
    public void deleteImage(String publicId) {
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

            String status = (String) result.get("result");

            if (!"ok".equals(status)
                    && !"not found".equals(status)) {
                throw new IllegalStateException(
                        "Image deletion failed: " + status
                );
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Image deletion failed", e
            );
        }
    }
}
