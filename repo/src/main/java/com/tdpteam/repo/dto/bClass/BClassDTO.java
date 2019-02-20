package com.tdpteam.repo.dto.bClass;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;

@Data
public class BClassDTO {
    @NotEmpty(message = "Please input the name of new class")
    private String name;
    @NotEmpty(message = "Please input the code of new class")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Please input a valid code of new class")
    private String code;
    @Min(value = 1)
    private Long teacherId;
    @Min(value = 1)
    private Long subjectId;
    @NotNull(message = "Please input the start date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    private ArrayList<Long> selectedStudents;
    private String[] calendarSelectedValues;
}
