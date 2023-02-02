package com.parentapp.parent_assist.users;

import com.parentapp.parent_assist.parents.Parents;
import com.parentapp.parent_assist.roles.Roles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Users {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, length = 35)
    private String username;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @Column
    private String parent;

    @Column
    private String personinfo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Roles> userRolesRoless;

    @OneToOne
    @JoinColumn(name = "parent_id_id", nullable = false)
    private Parents parentId;

}
