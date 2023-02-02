package com.parentapp.parent_assist.children;

import com.parentapp.parent_assist.event_activity.EventActivity;
import com.parentapp.parent_assist.event_milestone.EventMilestone;
import com.parentapp.parent_assist.event_test.EventTest;
import com.parentapp.parent_assist.relationships.Relationships;
import com.parentapp.parent_assist.subscription.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Children {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "childId")
    private Set<EventActivity> childIdEventActivitys;

    @OneToMany(mappedBy = "childId")
    private Set<EventMilestone> childIdEventMilestones;

    @OneToMany(mappedBy = "childId")
    private Set<Relationships> childIdRelationshipss;

    @OneToMany(mappedBy = "childId")
    private Set<Subscription> childIdSubscriptions;

    @OneToMany(mappedBy = "childId")
    private Set<EventTest> childIdEventTests;

}
