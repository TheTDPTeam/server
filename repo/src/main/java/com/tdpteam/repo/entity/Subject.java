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
@Table(name = "subject")
public class Subject extends BaseEntityAudit {
    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int order;

    @Column
    private int numberOfSection;

    @Column
    private boolean hasTheoryExamination = true;

    @Column
    private boolean hasPracticalExamination = true;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @OneToMany(mappedBy = "subject")
    private Set<BClass> bClasses = new HashSet<>();
}
