package com.parentapp.backend.subscription;

import com.parentapp.backend.child.Child;
import com.parentapp.backend.cashin.CashIn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Subscription {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private LocalDateTime start;

    @Column(name = "\"end\"")
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @OneToMany(mappedBy = "subscription")
    private Set<CashIn> subscriptionCashIns;

}