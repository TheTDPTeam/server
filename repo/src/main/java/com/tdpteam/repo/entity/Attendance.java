package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntity;
import com.tdpteam.repo.entity.user.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "attendance")
public class Attendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bClass_id", nullable = false)
    private BClass bClass;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status = AttendanceStatus.NotYet;

    @Column(name = "checking_date", nullable = false)
    private Date checkingDate;
}
