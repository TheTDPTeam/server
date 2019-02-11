package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.entity.user.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batch")
@Data
@EqualsAndHashCode(callSuper = false)
public class Batch extends BaseEntityAudit {
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private Date startDate;
    @Column
    private Date endDate;

    @OneToMany(mappedBy = "batch")
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
