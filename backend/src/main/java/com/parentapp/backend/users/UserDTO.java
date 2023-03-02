package com.parentapp.backend.users;

import com.parentapp.backend.auth.model.URole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    @Size(max = 35)
    private String username;

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 120)
    private String password;

    private Set<URole> userRole;

    @Size(max = 255)
    private String parentId;

}
