package com.parentapp.parent_assist.activities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivitiesDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    private String howTo;

    @NotNull
    private String rules;

    @NotNull
    private String description;

    @Size(max = 255)
    private String time;

    private AgeUnit ageUnit;

    private Integer ageFrom;

    private Integer ageTo;

    private List<String> activitiesMilestoness;

    private List<String> activityMaterialss;

    private List<String> activityPlacess;

    private List<String> activitySkillss;

}
