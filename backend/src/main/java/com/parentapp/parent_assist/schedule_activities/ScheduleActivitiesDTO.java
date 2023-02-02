package com.parentapp.parent_assist.schedule_activities;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleActivitiesDTO {

    @Size(max = 255)
    private String id;

    private LocalDate periodFrom;

    private LocalDate periodTo;

    @Size(max = 255)
    private String activityId;

}
