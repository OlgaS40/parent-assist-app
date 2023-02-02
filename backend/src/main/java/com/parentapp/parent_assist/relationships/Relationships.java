package com.parentapp.parent_assist.relationships;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.parents.Parents;
import com.parentapp.parent_assist.plan.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Relationships {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id_id", nullable = false)
    private Parents parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id_id")
    private Children childId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan planId;

}
