package com.parentapp.backend.milestone;

import com.parentapp.backend.activity.AgeUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MilestoneDTO {
    private String id;
    private String name;
    private String description;
    private AgeUnit ageUnit;
    private Integer ageFrom;
    private Integer ageTo;
    private Set<String> activities;

}
