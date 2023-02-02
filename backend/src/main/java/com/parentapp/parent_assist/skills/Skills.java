package com.parentapp.parent_assist.skills;

import com.parentapp.parent_assist.activities.Activities;
import com.parentapp.parent_assist.milestones.Milestones;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Skills {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "milestoneSkillsSkillss")
    private Set<Milestones> milestoneSkillsMilestoness;

    @ManyToMany(mappedBy = "activitySkillsSkillss")
    private Set<Activities> activitySkillsActivitiess;

}
