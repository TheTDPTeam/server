package com.tdpteam.repo.dto.semester;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SemesterDTO {
    @Getter
    @Setter
    private String description;

    private boolean isActivated;

    public boolean getIsActivated(){
        return this.isActivated;
    }

    public void setIsActivated(boolean isActivated){
        this.isActivated = isActivated;
    }

}
