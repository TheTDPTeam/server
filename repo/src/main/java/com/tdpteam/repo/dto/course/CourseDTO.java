package com.tdpteam.repo.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @NotEmpty(message = "Please input the name of new course")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Please input a valid name of new course")
    private String name;

    @NotEmpty(message = "Please input the code of new course")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Please input a valid code of new course")
    private String code;
}
