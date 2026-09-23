package com.bookflow.business.repository;

import com.bookflow.business.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<BusinessEntity,Long> {
}
