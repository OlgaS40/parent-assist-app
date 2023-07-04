package com.parentapp.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.parentapp.auth.model.AuthProvider;
import com.parentapp.auth.model.URole;
import com.parentapp.auth.model.User;
import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.response.MessageResponse;
import com.parentapp.auth.payload.response.OAuth2Response;
import com.parentapp.auth.payload.response.UserInfoResponse;
import com.parentapp.auth.security.jwt.JwtUtils;
import com.parentapp.auth.security.service.UserDetailsImpl;
import com.parentapp.users.UserDTO;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class OAuth2Service {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public OAuth2Service(AuthenticationService authenticationService, UserService userService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    public OAuth2Response signUpWithGoogle(String token) {
        // Initialize the GoogleIdTokenVerifier
        GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier();

        try {
            // Verify the idToken
            GoogleIdToken googleIdToken = verifier.verify(token);
            if (googleIdToken != null) {
                GoogleIdToken.Payload payload = googleIdToken.getPayload();

                // Extract the desired values from the payload
                String googleUserId = payload.getSubject();
                String email = payload.getEmail();
                String username = (String) payload.get("name");


                if (userService.existsByEmail(email)) {
                    return new OAuth2Response(false, new MessageResponse("Error: Email is already in use!"));
                }
                if (userService.existsByUsername(username)) {
                    username = email;
                }

                // Create new user's account
                UserDTO user = UserDTO.builder()
                        .username(username)
                        .email(email)
                        .password(encoder.encode(googleUserId))
                        .userRole(Collections.singleton(URole.USER))
                        .provider(AuthProvider.GOOGLE)
                        .enabled(true)
                        .build();

                userService.create(user);
                return new OAuth2Response(true, new MessageResponse("Registered Successfully!"));
            } else {
                // Invalid idToken
                // Handle the error appropriately
                return new OAuth2Response(false, new MessageResponse("Invalid idToken"));
            }
        } catch (Exception e) {
            // Exception occurred during verification
            // Handle the error appropriately
            return new OAuth2Response(false, new MessageResponse(e.getMessage()));
        }
    }


    public OAuth2Response loginWithGoogle(String idToken) {
        GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier();
        try {
            // Verify the idToken
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken != null) {
                GoogleIdToken.Payload payload = googleIdToken.getPayload();

                // Extract the desired values from the payload
                String email = payload.getEmail();

                if (userService.existsByEmail(email)) {
                    if (!userService.getByEmail(email).getProvider().equals(AuthProvider.GOOGLE)) {
                        return new OAuth2Response(false, new MessageResponse("Error: This email was used to register via username and password or Facebook!"));
                    }

                    String username = (String) payload.get("name");
                    String password = payload.getSubject();
                    return loginOAuth2(username, password);
                }

                return new OAuth2Response(false, new MessageResponse("Error: This email is not registered"));
            } else {
                // Invalid idToken
                // Handle the error appropriately
                return new OAuth2Response(false, new MessageResponse("Invalid idToken"));
            }
        } catch (Exception e) {
            // Exception occurred during verification
            // Handle the error appropriately
            return new OAuth2Response(false, new MessageResponse(e.getMessage()));
        }
    }

    private GoogleIdTokenVerifier getGoogleIdTokenVerifier() {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    public OAuth2Response signUpWithFacebook(String authToken) {
        UserDTO userFacebook = getFacebookUser(authToken);

        if (userService.existsByEmail(userFacebook.getEmail())) {
            return new OAuth2Response(false, new MessageResponse("Error: Email is already in use!"));
        }
        if (userService.existsByUsername(userFacebook.getUsername())) {
            userFacebook.setUsername(userFacebook.getEmail());
        }
        // Create new user's account
        UserDTO user = UserDTO.builder()
                .username(userFacebook.getUsername())
                .email(userFacebook.getEmail())
                .password(encoder.encode(userFacebook.getPassword()))
                .userRole(Collections.singleton(URole.USER))
                .provider(AuthProvider.FACEBOOK)
                .enabled(true)
                .build();

        userService.create(user);
        return new OAuth2Response(true, new MessageResponse("Registered Successfully!"));
    }

    public OAuth2Response loginWithFacebook(String authToken) {
        UserDTO userFacebook = getFacebookUser(authToken);

        if (userService.existsByEmail(userFacebook.getEmail())) {
            if (!userService.getByEmail(userFacebook.getEmail()).getProvider().equals(AuthProvider.FACEBOOK)) {
                return new OAuth2Response(false, new MessageResponse("Error: This email was used to register via username and password or Google!"));
            }
            return loginOAuth2(userFacebook.getUsername(), userFacebook.getPassword());
        }
        return new OAuth2Response(false, new MessageResponse("Error: This email is not registered"));
    }

    private OAuth2Response loginOAuth2(String username, String password) {
        UserDetailsImpl userDetails = authenticationService.login(new LoginRequest(username, password));
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        return new OAuth2Response(true, jwtCookie, new UserInfoResponse(token));
    }

    private UserDTO getFacebookUser(String token) {
//        Facebook facebook = new FacebookTemplate(token);
//        String[] data = {"email"};
//        User userFacebook = facebook.fetchObject("me", User.class, data);
//        return userFacebook;
        FacebookClient fbClient = new DefaultFacebookClient(token, Version.VERSION_5_0);

        try {
            // Fetch user information
            com.restfb.types.User user = fbClient.fetchObject("me", com.restfb.types.User.class, Parameter.with("fields", "id,name,email"));
            return UserDTO.builder()
                    .username(user.getName())
                    .email(user.getEmail())
                    .password(user.getId())
                    .build();
        } catch (FacebookException e) {
            // Handle any exceptions that occur during the API call
            e.printStackTrace();
            return null;
        }
    }
}
