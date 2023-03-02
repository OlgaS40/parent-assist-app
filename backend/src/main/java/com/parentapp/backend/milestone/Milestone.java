package com.parentapp.backend.milestone;

import com.parentapp.backend.activity.Activity;
import com.parentapp.backend.activity.AgeUnit;
import com.parentapp.backend.event_milestone.EventMilestone;
import com.parentapp.backend.skill.Skill;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
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
