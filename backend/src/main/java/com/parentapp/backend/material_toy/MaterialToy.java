package com.parentapp.backend.material_toy;

import com.parentapp.backend.activity.Activity;
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
public class MaterialToy {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String category;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private String imageUrl;

    @ManyToMany(mappedBy = "activityMaterials")
    private Set<Activity> materialToyActivities;

}
