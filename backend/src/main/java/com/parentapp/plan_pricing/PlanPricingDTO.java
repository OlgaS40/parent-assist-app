package com.parentapp.plan_pricing;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanPricingDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    @Size(max = 255)
    private String currency;

    @NotNull
    private Double price;

    @NotNull
    private LocalDateTime startingAt;

    private LocalDateTime endingAt;

    @NotNull
    @Size(max = 255)
    private String planId;

}
