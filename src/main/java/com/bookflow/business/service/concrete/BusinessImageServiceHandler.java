package com.bookflow.business.service.concrete;

import com.bookflow.business.dto.response.BusinessImageResponse;
import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.entity.BusinessImageEntity;
import com.bookflow.business.enums.BusinessImageType;
import com.bookflow.business.repository.BusinessImageRepository;
import com.bookflow.business.repository.BusinessRepository;
import com.bookflow.business.service.abstraction.BusinessImageService;
import com.bookflow.storage.dto.UploadResponse;
import com.bookflow.storage.service.abstraction.CloudinaryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BusinessImageServiceHandler implements BusinessImageService {
    private static final long MAX_FILE_SIZE = 20L * 1024 * 1024;
    private static final int MAX_GALLERY_IMAGES = 10;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final BusinessRepository businessRepository;
    private final BusinessImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    @Override
    @Transactional
    public BusinessImageResponse uploadImage(
            Long businessId,
            MultipartFile file,
            BusinessImageType type,
            Long ownerId
    ) {
        BusinessEntity business = getOwnedBusiness(businessId, ownerId);

        validateFile(file);

        if (type == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image type is required"
            );
        }

        if (type == BusinessImageType.GALLERY) {
            long count = imageRepository.countByBusiness_IdAndType(
                    businessId,
                    BusinessImageType.GALLERY
            );

            if (count >= MAX_GALLERY_IMAGES) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Maximum 10 gallery images allowed"
                );
            }
        } else {
            boolean exists = imageRepository
                    .findByBusiness_IdAndType(businessId, type)
                    .isPresent();

            if (exists) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        type + " already exists. Delete or replace it first."
                );
            }
        }

        String folder = "bookflow/businesses/"
                + businessId + "/"
                + type.name().toLowerCase();

        UploadResponse uploaded =  cloudinaryService.upload(file,folder);

        try {
            BusinessImageEntity image = BusinessImageEntity.builder()
                    .business(business)
                    .imageUrl(uploaded.imageUrl())
                    .publicId(uploaded.publicID())
                    .type(type)
                    .sortOrder(0)
                    .build();

            imageRepository.saveAndFlush(image);

            return toResponse(image);

        } catch (RuntimeException e) {
            try {
                cloudinaryService.deleteImage(uploaded.publicID());
            } catch (RuntimeException cleanupError) {
                e.addSuppressed(cleanupError);
            }
            throw e;
        }
    }

    @Override
    @Transactional
    public void setCover(
            Long businessId,
            Long imageId,
            Long ownerId
    ) {
        getOwnedBusiness(businessId, ownerId);

        BusinessImageEntity selected = imageRepository
                .findByIdAndBusiness_Id(imageId, businessId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Image not found"
                ));

        if (selected.getType() == BusinessImageType.LOGO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Logo cannot be used as cover"
            );
        }

        if (selected.getType() == BusinessImageType.COVER) {
            return;
        }

        BusinessImageEntity oldCover = imageRepository
                .findByBusiness_IdAndType(
                        businessId,
                        BusinessImageType.COVER
                )
                .orElse(null);

        if (oldCover != null) {
            long galleryCount =
                    imageRepository.countByBusiness_IdAndType(
                            businessId,
                            BusinessImageType.GALLERY
                    );

            if (galleryCount >= MAX_GALLERY_IMAGES) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Gallery is full. Delete an image first."
                );
            }

            oldCover.setType(BusinessImageType.GALLERY);
            imageRepository.saveAndFlush(oldCover);
        }

        selected.setType(BusinessImageType.COVER);
        imageRepository.saveAndFlush(selected);
    }

    @Override
    @Transactional
    public void deleteImage(
            Long businessId,
            Long imageId,
            Long ownerId
    ) {
        getOwnedBusiness(businessId, ownerId);

        BusinessImageEntity image = imageRepository
                .findByIdAndBusiness_Id(imageId, businessId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Image not found"
                ));

        cloudinaryService.deleteImage(image.getPublicId());

        imageRepository.delete(image);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessImageResponse> getImages(Long businessId) {

        if (!businessRepository.existsById(businessId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Business not found"
            );
        }

        return imageRepository
                .findByBusiness_IdOrderBySortOrderAsc(businessId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private BusinessEntity getOwnedBusiness(Long businessId, Long ownerId) {

        BusinessEntity business = businessRepository
                .findById(businessId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Business not found"
                ));

        if (ownerId == null ||
                !ownerId.equals(business.getOwner().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not the owner of this business"
            );
        }
        System.out.println("Business ID: " + businessId);
        System.out.println("Current owner ID: " + ownerId);
        System.out.println("Database owner ID: " + business.getOwner().getId());
        return business;
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image file is required"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum file size is 5MB"
            );
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only JPG, PNG and WebP are allowed"
            );
        }
    }

    private BusinessImageResponse toResponse(
            BusinessImageEntity image
    ) {
        return new BusinessImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getType(),
                image.getSortOrder()
        );
    }

}
