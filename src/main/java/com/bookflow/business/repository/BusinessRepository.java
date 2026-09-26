package com.bookflow.business.repository;

import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.enums.BusinessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<BusinessEntity,Long> {
    boolean existsByOwner_Id(Long ownerId);

    Optional<BusinessEntity> findByOwner_Id(Long ownerId);

    List<BusinessEntity> findAllByStatus(BusinessStatus status);

    Optional<BusinessEntity> findByOwner_IdAndStatusNot(Long ownerId, BusinessStatus status);


}
