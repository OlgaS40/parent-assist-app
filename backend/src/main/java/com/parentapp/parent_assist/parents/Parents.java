package com.parentapp.parent_assist.parents;

import com.parentapp.parent_assist.relationships.Relationships;
import com.parentapp.parent_assist.users.Users;
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
public class Parents {

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

    @OneToOne(mappedBy = "parentId", fetch = FetchType.LAZY)
    private Users parentId;

    @OneToMany(mappedBy = "parentId")
    private Set<Relationships> parentIdRelationshipss;

}
