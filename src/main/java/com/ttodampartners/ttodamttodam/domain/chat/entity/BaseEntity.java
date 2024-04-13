package com.ttodampartners.ttodamttodam.domain.chat.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;

//    @LastModifiedDate
//    private LocalDateTime updateAt;
}
