package com.parentapp.auth.repository;

import com.parentapp.auth.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    VerificationToken findByToken(String verificationToken);
}
