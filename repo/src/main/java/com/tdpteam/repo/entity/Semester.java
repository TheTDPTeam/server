package com.tdpteam.repo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tdpteam.repo.entity.base.BaseEntityAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "subjects")
@EqualsAndHashCode(callSuper = false, exclude = "subjects")
@Table(name = "semester")
public class Semester extends BaseEntityAudit {
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "semester", orphanRemoval = true)
    private Set<Subject> subjects = new HashSet<>();

    @Column
    private String description;
}
