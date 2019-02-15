package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.entity.user.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "score")
public class Score extends BaseEntityAudit {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column
    private float theoryScore = 0.0f;

    @Column
    private boolean theoryStatus = true;

    @Column
    private float practicalScore = 0.0f;

    @Column
    private boolean practicalStatus = true;
}
