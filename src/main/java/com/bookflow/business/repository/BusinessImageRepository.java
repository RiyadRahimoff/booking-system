package com.bookflow.business.repository;

import com.bookflow.business.entity.BusinessImageEntity;
import com.bookflow.business.enums.BusinessImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessImageRepository extends JpaRepository<BusinessImageEntity, Long> {
    // All images of the business
    List<BusinessImageEntity> findByBusiness_IdOrderBySortOrderAsc(Long businessId);
    // Find cover and logo
    Optional<BusinessImageEntity> findByBusiness_IdAndType(Long id, BusinessImageType type);
    // Images
    List<BusinessImageEntity> findAllByBusiness_IdAndTypeOrderBySortOrderAsc(Long businessId, BusinessImageType type);
    // Picture limit checker
    Long countByBusiness_IdAndType(Long businessId, BusinessImageType type);
    //Check photo belongs to business
    Optional<BusinessImageEntity> findByIdAndBusiness_Id(Long imageId,Long businessId);
}
