package com.tdpteam.repo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tdpteam.repo.entity.base.BaseEntity;
import com.tdpteam.repo.entity.user.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "attendance")
public class Attendance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "bClass_id", nullable = false)
    @JsonBackReference
    private BClass bClass;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
