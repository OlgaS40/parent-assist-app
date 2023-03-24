package com.parentapp.parent;

import com.parentapp.auth.model.User;
import com.parentapp.relationship.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Parent {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String surname;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String language;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private String postalCode;

    @OneToOne(mappedBy = "parent", fetch = FetchType.LAZY)
    private User parent;

    @OneToMany(mappedBy = "parent")
    private Set<Relationship> parentRelationship;

}
