package com.parentapp.parent_assist.materials_toys;

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
public class MaterialsToys {

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

    @ManyToMany(mappedBy = "activityMaterialsMaterialsToyss")
    private Set<Activities> activityMaterialsActivitiess;

}
