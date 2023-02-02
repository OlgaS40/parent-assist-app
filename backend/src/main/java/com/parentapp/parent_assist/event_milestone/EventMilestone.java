package com.parentapp.parent_assist.event_milestone;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.milestones.Milestones;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class EventMilestone {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id_id")
    private Milestones milestoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id_id")
    private Children childId;

}
