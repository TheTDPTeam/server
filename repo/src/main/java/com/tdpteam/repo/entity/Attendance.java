package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "attendance")
public class Attendance extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "bClass_id")
    @MapsId
    @NonNull
    private BClass bClass;
    private Date startDate;
    private Date endDate;
    private String fileUrl;
}
