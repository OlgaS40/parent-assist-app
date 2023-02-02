package com.parentapp.parent_assist.place;

import com.parentapp.parent_assist.activities.Activities;
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
public class Place {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "activityPlacesPlaces")
    private Set<Activities> activityPlacesActivities;

}
