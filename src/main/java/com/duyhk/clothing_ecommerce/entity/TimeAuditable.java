package com.duyhk.clothing_ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeAuditable {

    @Column(updatable = false)
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
