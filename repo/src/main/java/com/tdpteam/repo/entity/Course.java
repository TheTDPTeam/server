package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "course")
public class Course extends BaseEntityAudit {
    @Column
    private String name;

    @Column
    private String code;

    @OneToMany(mappedBy = "course")
    private Set<Semester> semesters = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Batch> batches = new HashSet<>();
}
