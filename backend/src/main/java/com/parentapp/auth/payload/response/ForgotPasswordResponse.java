package com.parentapp.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPasswordResponse {
    private boolean isSuccessful;
    private AuthResponse authResponse;

}
