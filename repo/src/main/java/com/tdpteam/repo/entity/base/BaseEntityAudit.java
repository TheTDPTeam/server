package com.tdpteam.repo.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseEntityAudit extends BaseEntity {

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Size(max = 20)
    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Size(max = 20)
    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "is_activated", nullable = false)
    private boolean isActivated = true;

    /**
     * Sets createdAt before insert
     */
    @PrePersist
    public void setCreationDate(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    /**
     * Sets updated before update
     */
    @PreUpdate
    public void setChangeDate(){
        this.updatedAt = new Date();
    }
}
