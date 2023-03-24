package com.parentapp.event_test;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventTestDTO {

    private String id;

    private LocalDateTime date;

    @Size(max = 255)
    private String option;

    @NotNull
    @Size(max = 255)
    private String testId;

    @NotNull
    @Size(max = 255)
    private String childId;

}
