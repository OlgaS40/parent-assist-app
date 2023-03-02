package com.parentapp.backend.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Role {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    @Enumerated(EnumType.STRING)
    private URole name;

    @ManyToMany(mappedBy = "userRole")
    private Set<User> userRole;

}
