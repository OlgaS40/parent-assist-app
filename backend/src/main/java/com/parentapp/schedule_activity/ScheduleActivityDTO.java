package com.parentapp.schedule_activity;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleActivityDTO {

    @Size(max = 255)
    private String id;

    private LocalDate periodFrom;

    private LocalDate periodTo;

    @Size(max = 255)
    private String activityId;

}
