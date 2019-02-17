package com.tdpteam.repo.entity;

import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.entity.user.Student;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.tdpteam.repo.helper.Constants.MINIMUM_PASSING_SCORE;

@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "score")
public class Score extends BaseEntityAudit {

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bClass_id", nullable = false)
    private BClass bClass;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Getter
    @Column
    private Integer theoryScore = null;

    @Setter
    @Column
    private boolean isTheoryScorePassed = false;

    @Getter
    @Column
    private Integer practicalScore = null;

    @Setter
    @Column
    private boolean isPracticalScorePassed = false;

    public boolean isTheoryScorePassed() {
        if(theoryScore != null){
            return theoryScore >= MINIMUM_PASSING_SCORE;
        }
        return false;
    }

    public boolean isPracticalScorePassed() {
        if(practicalScore != null){
            return practicalScore >= MINIMUM_PASSING_SCORE;
        }
        return false;
    }

    public void setTheoryScore(Integer theoryScore) {
        this.theoryScore = theoryScore;
        setTheoryScorePassed(theoryScore != null && theoryScore > MINIMUM_PASSING_SCORE);
    }

    public void setPracticalScore(Integer practicalScore) {
        this.practicalScore = practicalScore;
        setPracticalScorePassed(practicalScore != null && practicalScore > MINIMUM_PASSING_SCORE);
    }
}
