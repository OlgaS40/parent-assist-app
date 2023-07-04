package com.parentapp.auth.service;

import com.parentapp.auth.model.AuthProvider;
import com.parentapp.auth.model.URole;
import com.parentapp.auth.model.User;
import com.parentapp.auth.model.VerificationToken;
import com.parentapp.auth.payload.request.ForgotPasswordRequest;
import com.parentapp.auth.payload.request.LoginRequest;
import com.parentapp.auth.payload.request.SignUpRequest;
import com.parentapp.auth.payload.response.*;
import com.parentapp.auth.repository.VerificationTokenRepository;
import com.parentapp.auth.security.jwt.JwtUtils;
import com.parentapp.auth.security.service.UserDetailsImpl;
import com.parentapp.users.UserDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JwtAuthenticationService {
    @Value("${parentAssist.app.emailAddress}")
    private String appEmail;
    @Value("${mail.senderName}")
    private String senderName;
    @Value("${register.mail.subject}")
    private String registerMailSubject;
    @Value("${register.mail.content}")
    private String registerMailContent;
    @Value("${forgotPassword.mail.subject}")
    private String forgotPasswordMailSubject;
    @Value("${forgotPassword.mail.content}")
    private String forgotPasswordMailContent;
    @Value("${home.page}")
    private String homeUrl;

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationService authenticationService;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationService.class);
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$@$!%*?&";

    public JwtAuthenticationService(UserService userService,
                                    PasswordEncoder encoder,
                                    JwtUtils jwtUtils,
                                    AuthenticationService authenticationService, VerificationTokenRepository tokenRepository,
                                    EmailService emailService) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.authenticationService = authenticationService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }


    public JwtAuthResponse authenticateUser(LoginRequest loginRequest) {

        UserDetailsImpl userDetails = authenticationService.login(loginRequest);

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
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new SignUpResponse(false, new MessageResponse("Error: This username is already registered, please enter another one!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new SignUpResponse(false, new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserDTO user = UserDTO.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .userRole(getRoles(signUpRequest.getRole()))
                .provider(AuthProvider.APP)
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
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(appEmail, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(registerMailSubject);

        registerMailContent = registerMailContent.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + verificationToken.getToken();

        registerMailContent = registerMailContent.replace("[[URL]]", verifyURL);
        registerMailContent = registerMailContent.replace("[[URL-home]]", homeUrl);

        helper.setText(registerMailContent, true);

        addInlineImageToEmail(helper, "images/completeRegistration.jpg", "completeRegistration");
        addInlineImageToEmail(helper, "images/logoImage.png", "logoImage");

        emailService.sendEmail(message);
    }

    public SignUpResponse verifyUserForRegistration(String verificationCode) {
        VerificationToken token = tokenRepository.findByToken(verificationCode);
        if (Objects.isNull(token)) {
            return new SignUpResponse(false, new MessageResponse("Oops, it seams that your link expired or was already confirmed! Please contact us for more details."));
        }

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

    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException, UnsupportedEncodingException {
        if (!userService.existsByUsername(forgotPasswordRequest.getUsername())) {
            return new ForgotPasswordResponse(false, new MessageResponse("Error: Username is not registered!"));
        }

        if (!userService.existsByEmail(forgotPasswordRequest.getEmail())) {
            return new ForgotPasswordResponse(false, new MessageResponse("Error: Email is not registered!"));
        }

        User user = userService.getByUsername(forgotPasswordRequest.getUsername());
        if (!Objects.equals(user.getEmail(), forgotPasswordRequest.getEmail())) {
            return new ForgotPasswordResponse(false, new MessageResponse("Error: Username and email does not match!"));
        }
        // Create new password
        String password = generateRandomPassword(8);
        user.setPassword(encoder.encode(password));
        userService.update(user);

        UserDTO userDTO = userService.get(user.getId());
        sendForgotPasswordEmail(userDTO, password, forgotPasswordRequest.getUrl());
        return new ForgotPasswordResponse(true,
                new MessageResponse(String.format("Congratulation %s, you can now login our app with credentials provided in your email.", user.getUsername())));
    }

    private void sendForgotPasswordEmail(UserDTO user, String password, String url) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(appEmail, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(forgotPasswordMailSubject);

        forgotPasswordMailContent = forgotPasswordMailContent.replace("[[username]]", user.getUsername());
        forgotPasswordMailContent = forgotPasswordMailContent.replace("[[password]]", password);
        forgotPasswordMailContent = forgotPasswordMailContent.replace("[[URL]]", url);
        forgotPasswordMailContent = forgotPasswordMailContent.replace("[[URL-home]]", homeUrl);

        helper.setText(forgotPasswordMailContent, true);
        addInlineImageToEmail(helper, "images/logoImage.png", "logoImage");
        addInlineImageToEmail(helper, "images/resetPassword.jpg", "resetPassword");

        emailService.sendEmail(message);
    }

    private void addInlineImageToEmail(MimeMessageHelper helper, String relativePath, String imageName) throws MessagingException {
        URL imageUrl = getClass().getClassLoader().getResource(relativePath);
        if (imageUrl != null) {
            helper.addInline(imageName, new File(imageUrl.getFile()));
        } else {
            logger.warn("Image file not found.");
        }
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

    public static String generateRandomPassword(int length) {

        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder();

        // Add at least one lowercase letter
        passwordBuilder.append(Character.toLowerCase(CHARACTERS.charAt(random.nextInt(26))));

        // Add at least one uppercase letter
        passwordBuilder.append(Character.toUpperCase(CHARACTERS.charAt(random.nextInt(26))));

        // Add at least one digit
        passwordBuilder.append(CHARACTERS.charAt(random.nextInt(10) + 52));

        // Add at least one special character
        passwordBuilder.append("$@$!%*?&".charAt(random.nextInt(8)));

        // Add remaining characters
        for (int i = 0; i < length; i++) {
            passwordBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return passwordBuilder.toString();
    }
}
