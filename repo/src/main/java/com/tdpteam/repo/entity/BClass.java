package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.entity.user.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "bClass")
public class BClass extends BaseEntityAudit {
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(name = "bClass_student",
            joinColumns = @JoinColumn(name = "bClass_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bClass", optional = false)
    private Attendance attendance;
}