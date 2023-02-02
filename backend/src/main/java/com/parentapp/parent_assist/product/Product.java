package com.parentapp.parent_assist.product;

import com.parentapp.parent_assist.plan.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate createDate;

    @OneToMany(mappedBy = "productId")
    private Set<Plan> productIdPlans;

}
