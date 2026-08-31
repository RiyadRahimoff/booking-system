package com.bookflow.user.repository;

import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByStatusAndCreatedAtBefore(StatusEnum status, LocalDateTime createdAt);
}
