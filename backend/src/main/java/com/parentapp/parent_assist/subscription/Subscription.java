package com.parentapp.parent_assist.subscription;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.purchase.Purchase;
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
    @JoinColumn(name = "child_id_id", nullable = false)
    private Children childId;

    @OneToMany(mappedBy = "subscriptionId")
    private Set<Purchase> subscriptionIdPurchases;

}
