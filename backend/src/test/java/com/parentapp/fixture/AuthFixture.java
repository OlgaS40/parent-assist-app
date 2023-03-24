package com.parentapp.fixture;

import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.request.SignUpRequest;

import java.util.Set;

public class AuthFixture {
    private static final SignUpRequest signUpRequest;
    private static final LoginRequest loginRequest;

    static{
        signUpRequest = new SignUpRequest("ParentAssistAppTest1","testparentassist@gmail.com",
                Set.of("user"),"testparentassist");
        loginRequest = new LoginRequest("ParentAssistAppTest1","testparentassist");
    }
    public static SignUpRequest getSignUpRequest(){
        return signUpRequest;
    }
    public static LoginRequest getLoginRequest(){
        return loginRequest;
    }
}
