package com.parentapp.auth.controller;

import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.request.SignUpRequest;
import com.parentapp.auth.payload.response.JwtAuthResponse;
import com.parentapp.auth.service.JwtAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
    private final JwtAuthenticationService jwtAuthenticationService;
    public JwtAuthenticationController(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        JwtAuthResponse response = jwtAuthenticationService.authenticateUser(loginRequest);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .body(response.getAuthResponse());
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        return jwtAuthenticationService.registerUser(signUpRequest);
    }
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        JwtAuthResponse response = jwtAuthenticationService.logoutUser();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .body(response.getAuthResponse());
    }
}
