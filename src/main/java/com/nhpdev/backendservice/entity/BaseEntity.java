package com.nhpdev.backendservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private OffsetDateTime createAt;

    @LastModifiedDate
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private OffsetDateTime updatedAt;
}
