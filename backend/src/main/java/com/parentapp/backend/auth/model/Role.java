package com.parentapp.backend.auth.model;

import jakarta.persistence.*;

import java.util.Set;

import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    @Enumerated(EnumType.STRING)
    private URole name;

    @Column
    private String reqName;

    @ManyToMany(mappedBy = "userRole")
    private Set<User> userRole;

}
