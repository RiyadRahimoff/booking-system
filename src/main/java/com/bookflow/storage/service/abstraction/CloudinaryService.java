package com.bookflow.storage.service.abstraction;

import com.bookflow.storage.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    UploadResponse upload(MultipartFile file,String folder);
    void deleteImage (String publicId);
}
