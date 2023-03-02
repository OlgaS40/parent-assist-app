package com.parentapp.backend.event_milestone;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventMilestoneDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    private LocalDate date;

    @Size(max = 255)
    private String milestoneId;

    @Size(max = 255)
    private String childId;

}
