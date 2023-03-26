package com.parentapp.users;

import com.parentapp.auth.model.URole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
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
    private boolean enabled;

}
