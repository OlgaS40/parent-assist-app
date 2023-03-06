package com.parentapp.backend.auth.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse implements AuthResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

}
