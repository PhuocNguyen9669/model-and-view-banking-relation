package com.cg.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {


    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z", timezone = "/Asia/Ho_Chi_Minh")
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Column(name = "created_by")
    private Long createdBy;


    @JoinColumn(name = "updated_at")
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z", timezone = "/Asia/Ho_Chi_Minh")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

    }
