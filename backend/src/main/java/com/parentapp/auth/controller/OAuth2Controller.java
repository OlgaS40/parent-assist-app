package com.parentapp.auth.controller;

import com.parentapp.auth.payload.request.OAuth2SignInUpRequest;
import com.parentapp.auth.payload.response.OAuth2Response;
import com.parentapp.auth.service.OAuth2Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class OAuth2Controller {
    private final OAuth2Service oAuth2Service;

    public OAuth2Controller(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @PostMapping("/signup/google")
    public ResponseEntity<?> registerUserWithGoogle(@RequestBody OAuth2SignInUpRequest request) {
        OAuth2Response response = oAuth2Service.signUpWithGoogle(request.getToken());
        if (!response.isSuccessful()) {
            return ResponseEntity.badRequest().body(response.getAuthResponse());
        }
        return ResponseEntity.ok(response.getAuthResponse());
    }

    @PostMapping("/signin/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody OAuth2SignInUpRequest request) {
        OAuth2Response response = oAuth2Service.loginWithGoogle(request.getToken());
        if (!response.isSuccessful()) {
            return ResponseEntity.badRequest().body(response.getAuthResponse());
        }
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .body(response.getAuthResponse());
    }

    @PostMapping("/signup/facebook")
    public ResponseEntity<?> registerUserWithFacebook(@RequestBody OAuth2SignInUpRequest request){
        OAuth2Response response = oAuth2Service.signUpWithFacebook(request.getToken());
        if (!response.isSuccessful()) {
            return ResponseEntity.badRequest().body(response.getAuthResponse());
        }
        return ResponseEntity.ok(response.getAuthResponse());
    }

    @PostMapping("/signin/facebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody OAuth2SignInUpRequest request){
        OAuth2Response response = oAuth2Service.loginWithFacebook(request.getToken());
        if (!response.isSuccessful()) {
            return ResponseEntity.badRequest().body(response.getAuthResponse());
        }
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.getResponseCookie().toString())
                .body(response.getAuthResponse());
    }
}
