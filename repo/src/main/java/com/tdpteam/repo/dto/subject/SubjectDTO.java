package com.tdpteam.repo.dto.subject;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    @NotEmpty(message = "Please input the name of new subject")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Please input a valid name of new subject")
    @Getter @Setter private String name;

    @Getter @Setter private String description;
    @Getter @Setter private int subjectOrder;

    @Min(value = 1, message = "Number of sections cannot less than 1")
    @Getter @Setter private int numberOfLessons;
    @Getter @Setter private boolean hasTheoryExamination = true;
    @Getter @Setter private boolean hasPracticalExamination = true;

    @NotNull
    @Getter @Setter private Long semesterId;

    private boolean isActivated;

    public boolean getIsActivated(){
        return this.isActivated;
    }

    public void setIsActivated(boolean isActivated){
        this.isActivated = isActivated;
    }
}
