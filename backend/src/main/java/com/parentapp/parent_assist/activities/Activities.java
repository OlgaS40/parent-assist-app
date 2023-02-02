package com.parentapp.parent_assist.activities;

import com.parentapp.parent_assist.event_activity.EventActivity;
import com.parentapp.parent_assist.materials_toys.MaterialsToys;
import com.parentapp.parent_assist.milestones.Milestones;
import com.parentapp.parent_assist.place.Place;
import com.parentapp.parent_assist.schedule_activities.ScheduleActivities;
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
public class Activities {

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
            name = "activities_milestones",
            joinColumns = @JoinColumn(name = "activities_id"),
            inverseJoinColumns = @JoinColumn(name = "milestones_id")
    )
    private Set<Milestones> activitiesMilestonesMilestoness;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_materials",
            joinColumns = @JoinColumn(name = "activities_id"),
            inverseJoinColumns = @JoinColumn(name = "materials_toys_id")
    )
    private Set<MaterialsToys> activityMaterialsMaterialsToyss;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_places",
            joinColumns = @JoinColumn(name = "activities_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    private Set<Place> activityPlacesPlaces;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "activity_skills",
            joinColumns = @JoinColumn(name = "activities_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Set<Skills> activitySkillsSkillss;

    @OneToMany(mappedBy = "activityId")
    private Set<ScheduleActivities> activityIdScheduleActivitiess;

    @OneToMany(mappedBy = "activityId")
    private Set<EventActivity> activityIdEventActivitys;

}
