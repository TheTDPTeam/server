package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.entity.user.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"attendances", "students", "teacher", "subject", "scores"})
@EqualsAndHashCode(callSuper = false, exclude = {"attendances","students", "teacher", "subject", "scores"})
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
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column
    private Date startDate;
    @Column
    private Date estimatedEndDate;

    @OneToMany(mappedBy = "bClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "bClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Score> scores = new HashSet<>();

    @Column
    private String calendar;
}
