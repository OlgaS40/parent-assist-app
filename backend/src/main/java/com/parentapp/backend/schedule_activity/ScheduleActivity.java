package com.parentapp.backend.schedule_activity;

import com.parentapp.backend.activity.Activity;
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
public class ScheduleActivity {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private LocalDate periodFrom;

    @Column
    private LocalDate periodTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

}
