package com.parentapp.parent_assist.plan;

import com.parentapp.parent_assist.plan_pricing.PlanPricing;
import com.parentapp.parent_assist.product.Product;
import com.parentapp.parent_assist.relationships.Relationships;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Plan {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String interval;

    @OneToMany(mappedBy = "planId")
    private Set<PlanPricing> planIdPlanPricings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id_id", nullable = false)
    private Product productId;

    @OneToMany(mappedBy = "planId")
    private Set<Relationships> planIdRelationshipss;

}
