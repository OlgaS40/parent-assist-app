package com.parentapp.parent_assist.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersDTO {

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

    @Size(max = 255)
    private String parent;

    @Size(max = 255)
    private String personinfo;

    private List<String> userRoless;

    @NotNull
    @Size(max = 255)
    private String parentId;

}
