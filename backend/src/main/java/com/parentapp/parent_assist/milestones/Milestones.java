package com.parentapp.parent_assist.milestones;

import com.parentapp.parent_assist.activities.Activities;
import com.parentapp.parent_assist.activities.AgeUnit;
import com.parentapp.parent_assist.event_milestone.EventMilestone;
import com.parentapp.parent_assist.skills.Skills;
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
public class Milestones {

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

    @ManyToMany(mappedBy = "activitiesMilestonesMilestoness")
    private Set<Activities> activitiesMilestonesActivitiess;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "milestone_skills",
            joinColumns = @JoinColumn(name = "milestones_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Set<Skills> milestoneSkillsSkillss;

    @OneToMany(mappedBy = "milestoneId")
    private Set<EventMilestone> milestoneIdEventMilestones;

}
