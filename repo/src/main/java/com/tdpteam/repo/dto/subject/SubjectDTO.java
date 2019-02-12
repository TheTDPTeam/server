package com.tdpteam.repo.dto.subject;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private int subjectOrder;
    @Getter @Setter private int numberOfSection;
    @Getter @Setter private boolean hasTheoryExamination = true;
    @Getter @Setter private boolean hasPracticalExamination = true;

    private boolean isActivated;

    public boolean getIsActivated(){
        return this.isActivated;
    }

    public void setIsActivated(boolean isActivated){
        this.isActivated = isActivated;
    }
}
