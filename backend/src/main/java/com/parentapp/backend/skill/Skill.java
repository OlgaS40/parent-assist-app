package com.parentapp.backend.skill;

import com.parentapp.backend.activity.Activity;
import com.parentapp.backend.milestone.Milestone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;

import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class Skill {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "milestoneSkills")
    private Set<Milestone> skillsMilestones;

    @ManyToMany(mappedBy = "activitySkills")
    private Set<Activity> skillActivities;

}
