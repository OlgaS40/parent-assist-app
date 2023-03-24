package com.parentapp.auth.model;


import java.util.Arrays;

public enum URole {

    USER("user"),
    ADMIN("admin"),
    CONTENT_MAKER("content_maker");

    private String role;

    URole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static URole getEnum(String role) {
        return Arrays.stream(URole.values())
                .filter(urole -> urole.getRole().equals(role))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
