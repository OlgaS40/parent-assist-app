package com.parentapp.backend.plan;

import com.parentapp.backend.plan_pricing.PlanPricing;
import com.parentapp.backend.product.Product;
import com.parentapp.backend.relationship.Relationship;
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

    @OneToMany(mappedBy = "plan")
    private Set<PlanPricing> planPricingSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "plan")
    private Set<Relationship> planRelationships;

}
