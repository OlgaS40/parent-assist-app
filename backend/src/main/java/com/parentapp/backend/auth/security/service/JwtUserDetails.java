package com.parentapp.backend.auth.security.service;

import com.parentapp.backend.auth.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails {
    private String userId;
    private String username;
    private String email;
    private String password;
    private boolean locked;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(String userId,
                          String username,
                          String email,
                          String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    public static JwtUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getUserRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new JwtUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
