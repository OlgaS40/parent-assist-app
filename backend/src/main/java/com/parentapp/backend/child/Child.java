package com.parentapp.backend.child;

import com.parentapp.backend.event_activity.EventActivity;
import com.parentapp.backend.event_milestone.EventMilestone;
import com.parentapp.backend.event_test.EventTest;
import com.parentapp.backend.relationship.Relationship;
import com.parentapp.backend.subscription.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Child {

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

    @OneToMany(mappedBy = "child")
    private Set<EventActivity> childEventActivity;

    @OneToMany(mappedBy = "child")
    private Set<EventMilestone> childEventMilestone;

    @OneToMany(mappedBy = "child")
    private Set<Relationship> childRelationship;

    @OneToMany(mappedBy = "child")
    private Set<Subscription> childSubscription;

    @OneToMany(mappedBy = "child")
    private Set<EventTest> childEventTest;

}
