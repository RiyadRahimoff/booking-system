package com.bookflow.admin.repository;

import com.bookflow.user.entity.UserEntity;
import liquibase.license.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<UserEntity,Long> {
}
