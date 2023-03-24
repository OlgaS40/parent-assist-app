package com.parentapp.auth.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserInfoResponse implements AuthResponse {
    private String id;
    private String username;
    private String email;
    private List<String> roles;

    public UserInfoResponse(String id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
