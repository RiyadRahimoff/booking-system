package com.bookflow.business.repository;

import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.enums.BusinessStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<BusinessEntity,Long> {
    boolean existsByOwner_Id(Long ownerId);

    Optional<BusinessEntity> findByOwner_Id(Long ownerId);

    Page<BusinessEntity> findAllByStatus(BusinessStatus status, Pageable pageable);

    Optional<BusinessEntity> findByOwner_IdAndStatusNot(Long ownerId, BusinessStatus status);


}
