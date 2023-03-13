package com.parentapp.backend.milestone;

import com.parentapp.backend.activity.Activity;
import com.parentapp.backend.activity.AgeUnit;
import com.parentapp.backend.event_milestone.EventMilestone;
import com.parentapp.backend.skill.Skill;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Milestone {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private AgeUnit ageUnit;

    @Column
    private Integer ageFrom;

    @Column
    private Integer ageTo;

    @ManyToMany(mappedBy = "activityMilestones")
    private Set<Activity> milestoneActivities;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "milestone_skills",
            joinColumns = @JoinColumn(name = "milestones_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Set<Skill> milestoneSkills;

    @OneToMany(mappedBy = "milestone")
    private Set<EventMilestone> milestoneEventMilestone;

}
