package com.parentapp.services;

import com.parentapp.auth.security.jwt.JwtUtils;
import com.parentapp.auth.security.service.UserDetailsImpl;
import com.parentapp.auth.model.User;
import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.request.SignUpRequest;
import com.parentapp.auth.payload.response.JwtAuthResponse;
import com.parentapp.auth.payload.response.MessageResponse;
import com.parentapp.auth.payload.response.UserInfoResponse;
import com.parentapp.auth.repository.RoleRepository;
import com.parentapp.auth.repository.UserRepository;
import com.parentapp.auth.service.JwtAuthenticationService;
import com.parentapp.fixture.AuthFixture;
import com.parentapp.fixture.RoleFixture;
import com.parentapp.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private JwtAuthenticationService jwtAuthenticationService;
    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    private User testUser;

    @BeforeEach
    public void setup(){
        signUpRequest = AuthFixture.getSignUpRequest();
        loginRequest = AuthFixture.getLoginRequest();
        testUser = UserFixture.testUser();
    }
    @Test
    void registerUser_signUpRequest_userCreatedSuccessfullyResponse(){
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByReqNameIn(anySet())).thenReturn(Set.of(RoleFixture.userRole));

//        ResponseEntity<?> response =  jwtAuthenticationService.registerUser(signUpRequest);

//        verify(userRepository, times(1)).save(any(User.class));
//        assertThat(response).isNotNull();
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(new MessageResponse("User registered successfully!"), response.getBody());
    }
    @Test
    void registerUser_signUpRequestWithEmailAlreadyExists_badResponse(){
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

//        ResponseEntity<?> response =  jwtAuthenticationService.registerUser(signUpRequest);

//        verify(userRepository, times(0)).save(any(User.class));
//        assertThat(response).isNotNull();
//        assertTrue(response.getStatusCode().is4xxClientError());
//        assertEquals(new MessageResponse("Error: Email is already in use!"), response.getBody());
    }
    @Test
    void registerUser_signUpRequestWithUsernameAlreadyExists_badResponse(){
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

//        ResponseEntity<?> response =  jwtAuthenticationService.registerUser(signUpRequest);

//        verify(userRepository, times(0)).save(any(User.class));
//        assertThat(response).isNotNull();
//        assertTrue(response.getStatusCode().is4xxClientError());
//        assertEquals(new MessageResponse("Error: Username is already taken!"), response.getBody());
    }
    @Test
    void authenticateUser_loginRequest_JwtAuthResponse() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())))
                .thenReturn(authentication);

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("USER")));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUserId()).thenReturn(testUser.getId());
        when(userDetails.getUsername()).thenReturn(testUser.getUsername());
        when(userDetails.getEmail()).thenReturn(testUser.getEmail());

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        when(jwtUtils.generateJwtCookie(userDetails)).thenReturn(jwtCookie);

        JwtAuthResponse jwtAuthResponse = jwtAuthenticationService.authenticateUser(loginRequest);

        assertEquals(jwtCookie, jwtAuthResponse.getResponseCookie());
        UserInfoResponse userInfoResponse = (UserInfoResponse) jwtAuthResponse.getAuthResponse();
        assertEquals(testUser.getId(), userInfoResponse.getId());
        assertEquals(testUser.getUsername(), userInfoResponse.getUsername());
        assertEquals(testUser.getEmail(), userInfoResponse.getEmail());
    }
}
