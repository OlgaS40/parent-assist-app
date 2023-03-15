package com.parentapp.fixture;

import com.parentapp.auth.model.Role;
import com.parentapp.auth.model.URole;

import java.util.List;

public class RoleFixture {
    public static final Role userRole;
    public static final Role adminRole;
    public static final Role contentMakerRole;

    static {
        userRole = Role.builder()
                .id("userRoleTest")
                .name(URole.USER)
                .reqName("user")
                .build();
        adminRole = Role.builder()
                .id("adminRoleTest")
                .name(URole.ADMIN)
                .reqName("admin")
                .build();
        contentMakerRole = Role.builder()
                .id("contentMakerRoleTest")
                .name(URole.CONTENT_MAKER)
                .reqName("content_maker")
                .build();
    }
    public static List<Role> roleList(){
        return List.of(userRole, adminRole, contentMakerRole);
    }
}
