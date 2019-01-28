package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntity;
import com.tdpteam.repo.entity.user.Student;
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

    @ManyToOne
    @JoinColumn(name = "bClass_id", nullable = false)
    private BClass bClass;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
