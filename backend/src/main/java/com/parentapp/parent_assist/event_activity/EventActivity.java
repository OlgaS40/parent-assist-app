package com.parentapp.parent_assist.event_activity;

import com.parentapp.parent_assist.activities.Activities;
import com.parentapp.parent_assist.children.Children;
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
public class EventActivity {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String dificulty;

    @Column(nullable = false)
    private String interest;

    @Column(columnDefinition = "text")
    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id_id", nullable = false)
    private Activities activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id_id", nullable = false)
    private Children childId;

}
