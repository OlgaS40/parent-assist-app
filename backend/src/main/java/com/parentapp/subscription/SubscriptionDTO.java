package com.parentapp.subscription;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
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
