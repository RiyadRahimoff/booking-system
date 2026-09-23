package com.bookflow.business.entity;

import com.bookflow.business.enums.BusinessStatus;
import com.bookflow.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "business")
@EqualsAndHashCode(of = "id")

@FieldDefaults(level = PRIVATE)
public class BusinessEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;

    String description;

    String phone;

    String email;

    String address;

    @Enumerated(value = STRING)
    BusinessStatus status;

    String city;

    BigDecimal latitude;

    BigDecimal longitude;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    UserEntity owner;

}
