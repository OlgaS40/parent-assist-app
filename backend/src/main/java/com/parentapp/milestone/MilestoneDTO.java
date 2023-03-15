package com.parentapp.milestone;

import com.parentapp.activity.AgeUnit;
import lombok.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
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
