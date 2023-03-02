package com.parentapp.backend.activity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivityDTO {

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

    private List<String> activityMilestones;

    private List<String> activityMaterials;

    private List<String> activityPlaces;

    private List<String> activitySkills;

}
