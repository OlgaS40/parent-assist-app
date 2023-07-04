package com.parentapp.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@Getter
@Setter
public class OAuth2Response {
    private boolean isSuccessful;
    private ResponseCookie responseCookie;
    private AuthResponse authResponse;

    public OAuth2Response(boolean isSuccessful, ResponseCookie responseCookie, AuthResponse authResponse) {
        this.isSuccessful = isSuccessful;
        this.responseCookie = responseCookie;
        this.authResponse = authResponse;
    }

    public OAuth2Response(boolean isSuccessful, AuthResponse authResponse) {
        this.isSuccessful = isSuccessful;
        this.authResponse = authResponse;
    }
}
