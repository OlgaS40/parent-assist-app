package com.parentapp.auth.service;

import com.parentapp.auth.model.URole;
import com.parentapp.auth.model.VerificationToken;
import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.request.SignUpRequest;
import com.parentapp.auth.payload.response.JwtAuthResponse;
import com.parentapp.auth.payload.response.MessageResponse;
import com.parentapp.auth.payload.response.SignUpResponse;
import com.parentapp.auth.payload.response.UserInfoResponse;
import com.parentapp.auth.repository.VerificationTokenRepository;
import com.parentapp.auth.security.jwt.JwtUtils;
import com.parentapp.auth.security.service.UserDetailsImpl;
import com.parentapp.email_notify.EmailService;
import com.parentapp.users.UserDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class JwtAuthenticationService {
    @Value("${parentAssist.app.emailAddress}")
    private String appEmail;
    @Value("${register.mail.senderName}")
    private String senderName;
    @Value("${register.mail.subject}")
    private String mailSubject;
    @Value("${register.mail.content}")
    private String mailContent;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    public JwtAuthenticationService(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    PasswordEncoder encoder,
                                    JwtUtils jwtUtils,
                                    VerificationTokenRepository tokenRepository,
                                    EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
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

    public SignUpResponse registerUser(SignUpRequest signUpRequest, String siteURL) throws MessagingException, UnsupportedEncodingException {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new SignUpResponse(false, new MessageResponse("Error: Email is already in use!"));
        }

        if (userService.existsByUsername(signUpRequest.getUsername()) && userService.existsByEmail(signUpRequest.getEmail())) {
            return new SignUpResponse(false, new MessageResponse("Error: Username and email provided are already registered!"));
        }

        // Create new user's account
        UserDTO user = UserDTO.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .userRole(getRoles(signUpRequest.getRole()))
                .enabled(false)
                .build();

        String userId = userService.create(user);

        // Create verification token for registration confirm
        String token = RandomString.make(64);
        VerificationToken verificationToken = userService.createVerificationTokenForUser(userId, token);

        //Email with verification token
        sendVerificationEmail(user, siteURL, verificationToken);
        return new SignUpResponse(true, new MessageResponse("Complete Registration by the link sent on your email address"));
    }

    private void sendVerificationEmail(UserDTO user, String siteURL, VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(appEmail, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(mailSubject);

        mailContent = mailContent.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + verificationToken.getToken();

        mailContent = mailContent.replace("[[URL]]", verifyURL);

        helper.setText(mailContent, true);

        emailService.sendEmail(message);
    }

    public SignUpResponse verifyUserForRegistration(String verificationCode) {
        VerificationToken token = tokenRepository.findByToken(verificationCode);
        UserDTO user = userService.get(token.getUser().getId());

        if (Objects.isNull(user)) {
            return new SignUpResponse(false, new MessageResponse("User does not exist"));
        }

        if (user.isEnabled()) {
            String message = "User %s is already active in our app." + user.getUsername();
            return new SignUpResponse(false, new MessageResponse(message));
        }

        //if user is found by the given verification code - the app activate the user account
        user.setEnabled(true);
        userService.update(user.getId(), user);
        //delete token
        tokenRepository.delete(token);

        return new SignUpResponse(true,
                new MessageResponse(String.format("Congratulation %s, you are successfully registered in our app.", user.getUsername())));

    }

    public JwtAuthResponse logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return new JwtAuthResponse(cookie, new MessageResponse("You've been signed out!"));
    }

    private Set<URole> getRoles(Set<String> strRoles) {
        return Objects.isNull(strRoles)
                ? Collections.singleton(URole.USER)
                : strRoles.stream().map(role -> URole.getEnum(role)).collect(Collectors.toSet());
    }
}
