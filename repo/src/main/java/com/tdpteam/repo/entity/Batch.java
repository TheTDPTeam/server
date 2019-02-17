package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.entity.user.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batch")
@Data
@ToString(exclude = "students")
@EqualsAndHashCode(callSuper = false, exclude = "students")
public class Batch extends BaseEntityAudit {
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private Date startDate;

    @OneToMany(mappedBy = "batch")
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
