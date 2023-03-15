package com.parentapp.auth.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@Getter
@Setter
public class JwtAuthResponse {
    private ResponseCookie responseCookie;
    private AuthResponse authResponse;

    public JwtAuthResponse(ResponseCookie responseCookie, AuthResponse authResponse) {
        this.responseCookie = responseCookie;
        this.authResponse = authResponse;
    }
}
