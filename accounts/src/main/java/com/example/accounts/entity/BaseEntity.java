package com.example.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
@MappedSuperclass //this class will act as superclass for entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)// AuditingEntityListener class is present in spring data JPa, to use auditing features
public class BaseEntity {
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false,name = "created_at") // it should not be updated
    LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false,name = "created_by") // it should not be updated
    String createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false,name = "updated_At") // when first time a record is enetered this value should be null
    LocalDateTime updatedAt;

@LastModifiedBy
    @Column(insertable = false,name = "updated_by")
    String updatedBy;

}
//