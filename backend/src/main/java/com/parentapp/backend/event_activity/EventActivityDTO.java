package com.parentapp.backend.event_activity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventActivityDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(max = 255)
    private String difficulty;

    @NotNull
    @Size(max = 255)
    private String interest;

    private String feedback;

    @NotNull
    @Size(max = 255)
    private String activityId;

    @NotNull
    @Size(max = 255)
    private String childId;

}
