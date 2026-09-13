package com.bookflow.auth.repository;

import com.bookflow.auth.entity.RefreshTokenEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
    Optional<RefreshTokenEntity> findUserById(Long userId);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.user.id = :id")
    void deleteUserById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiresAt < :now")
    void deleteAllExpired(@Param("now") LocalDateTime now);
}
