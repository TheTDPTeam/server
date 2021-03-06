package com.tdpteam.repo.dto.batch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class BatchDTO {
    @Getter
    @Setter
    @NotEmpty(message = "Please input the name of new batch")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Please input a valid name of new batch")
    private String name;

    @Getter
    @Setter
    @NotEmpty(message = "Please input the code of new batch")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Please input a valid code of new batch")
    private String code;

    @Getter
    @Setter
    @NotNull(message = "Please input the start date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Getter
    @Setter
    @Min(value = 1)
    private Long courseId;

    private boolean isActivated;

    public boolean getIsActivated(){
        return this.isActivated;
    }

    public void setIsActivated(boolean isActivated){
        this.isActivated = isActivated;
    }
}
