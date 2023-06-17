package com.parentapp.auth.controller;

import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.request.SignUpRequest;
import com.parentapp.auth.payload.response.JwtAuthResponse;
import com.parentapp.auth.payload.response.SignUpResponse;
import com.parentapp.auth.service.JwtAuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class JwtAuthenticationController {
    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtAuthenticationController(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @GetMapping("/signup/verify")
    public ResponseEntity<?> verifyUser(@Param("code") String code) {
        SignUpResponse response = jwtAuthenticationService.verifyUserForRegistration(code);

        if (!response.isSuccessful()) {
            return ResponseEntity.badRequest().body(response.getAuthResponse());
        }

        return ResponseEntity.ok(response.getAuthResponse());

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, @RequestHeader("X-URL") String url)
            throws UnsupportedEncodingException, MessagingException {

        SignUpResponse response = jwtAuthenticationService.registerUser(signUpRequest, url);
        if (!response.isSuccessful()) {
            return ResponseEntity.badRequest().body(response.getAuthResponse());
        }
        return ResponseEntity.ok(response.getAuthResponse());
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        JwtAuthResponse response = jwtAuthenticationService.authenticateUser(loginRequest);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .body(response.getAuthResponse());
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        JwtAuthResponse response = jwtAuthenticationService.logoutUser();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .body(response.getAuthResponse());
    }
}
