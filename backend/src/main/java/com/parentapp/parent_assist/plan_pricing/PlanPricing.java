package com.parentapp.parent_assist.plan_pricing;

import com.parentapp.parent_assist.plan.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PlanPricing {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime startingAt;

    @Column
    private LocalDateTime endingAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan planId;

}
