package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"semesters", "batches"})
@EqualsAndHashCode(callSuper = false, exclude = {"semesters", "batches"})
@Table(name = "course")
public class Course extends BaseEntityAudit {
    @Column
    private String name;

    @Column
    private String code;

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private Set<Semester> semesters = new HashSet<>();

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private Set<Batch> batches = new HashSet<>();
}
