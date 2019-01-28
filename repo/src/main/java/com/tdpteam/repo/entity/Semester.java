package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "semester")
public class Semester extends BaseEntityAudit {
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "semester")
    private Set<Subject> subjects = new HashSet<>();

    @Column
    private String description;
}
