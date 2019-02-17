package com.tdpteam.repo.dto.course;

import com.tdpteam.repo.entity.Batch;
import com.tdpteam.repo.entity.Semester;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailDTO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private List<Semester> semesters;
    @Getter
    @Setter
    private List<Batch> batches;

    private boolean isActivated;

    public boolean getIsActivated(){
        return this.isActivated;
    }

    public void setIsActivated(boolean isActivated){
        this.isActivated = isActivated;
    }
}
