package com.parentapp.backend.auth.service;

import com.parentapp.backend.auth.model.Role;
import com.parentapp.backend.auth.model.URole;
import com.parentapp.backend.auth.model.User;
import com.parentapp.backend.auth.payload.request.LoginRequest;
import com.parentapp.backend.auth.payload.request.SignUpRequest;
import com.parentapp.backend.auth.payload.response.JwtAuthResponse;
import com.parentapp.backend.auth.payload.response.MessageResponse;
import com.parentapp.backend.auth.payload.response.UserInfoResponse;
import com.parentapp.backend.auth.repository.RoleRepository;
import com.parentapp.backend.auth.repository.UserRepository;
import com.parentapp.backend.auth.security.jwt.JwtUtils;
import com.parentapp.backend.auth.security.service.UserDetailsImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public JwtAuthenticationService(AuthenticationManager authenticationManager,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    PasswordEncoder encoder,
                                    JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }
    public JwtAuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtAuthResponse(jwtCookie, new UserInfoResponse(userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = getRoles(signUpRequest.getRole());

        user.setUserRole(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    public JwtAuthResponse logoutUser(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return new JwtAuthResponse(cookie, new MessageResponse("You've been signed out!"));
    }
    private Set<Role> getRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(URole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(URole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "content_maker" -> {
                        Role cmRole = roleRepository.findByName(URole.CONTENT_MAKER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(cmRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(URole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }
        return roles;
    }
}
