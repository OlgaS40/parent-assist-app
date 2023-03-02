package com.parentapp.backend.subscription;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubscriptionDTO {

    @Size(max = 255)
    private String id;

    private LocalDateTime start;

    private LocalDateTime end;

    @NotNull
    @Size(max = 255)
    private String childId;

}
