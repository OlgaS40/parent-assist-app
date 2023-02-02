package com.parentapp.parent_assist.roles;

import com.parentapp.parent_assist.users.Users;
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
public class Roles {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    @Enumerated(EnumType.STRING)
    private URoles name;

    @ManyToMany(mappedBy = "userRolesRoless")
    private Set<Users> userRolesUserss;

}
