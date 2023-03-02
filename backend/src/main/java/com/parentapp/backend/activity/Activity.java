package com.parentapp.backend.activity;

import com.parentapp.backend.event_activity.EventActivity;
import com.parentapp.backend.material_toy.MaterialToy;
import com.parentapp.backend.milestone.Milestone;
import com.parentapp.backend.place.Place;
import com.parentapp.backend.schedule_activity.ScheduleActivity;
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
public class Activity {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String howTo;

    @Column(nullable = false, columnDefinition = "text")
    private String rules;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column
    private String time;

    @Column
    @Enumerated(EnumType.STRING)
    private AgeUnit ageUnit;

    @Column
    private Integer ageFrom;

    @Column
    private Integer ageTo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_milestone",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "milestone_id")
    )
    private Set<Milestone> activityMilestones;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_materials",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "materials_toys_id")
    )
    private Set<MaterialToy> activityMaterials;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_places",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    private Set<Place> activityPlaces;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_skills",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Set<Skill> activitySkills;

    @OneToMany(mappedBy = "activity")
    private Set<ScheduleActivity> activityScheduleActivities;

    @OneToMany(mappedBy = "activity")
    private Set<EventActivity> activityEventActivity;

}
