package com.tdpteam.repo.dto.course;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CourseDTO {
    @Getter
    @Setter
    @NotEmpty(message = "Please input the name of new course")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Please input a valid name of new course")
    private String name;

    @Getter
    @Setter
    @NotEmpty(message = "Please input the code of new course")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Please input a valid code of new course")
    private String code;

    private boolean isActivated;

    public boolean getIsActivated(){
        return this.isActivated;
    }

    public void setIsActivated(boolean isActivated){
        this.isActivated = isActivated;
    }
}
