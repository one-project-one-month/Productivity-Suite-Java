package com._p1m.productivity_suite.security.service.impl;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.service.EmailService;
import com._p1m.productivity_suite.config.utils.DtoUtil;
import com._p1m.productivity_suite.config.utils.EntityUtil;
import com._p1m.productivity_suite.data.enums.Gender;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import com._p1m.productivity_suite.security.dto.LoginRequest;
import com._p1m.productivity_suite.security.dto.RegisterRequest;
import com._p1m.productivity_suite.security.dto.ResetPasswordRequest;
import com._p1m.productivity_suite.security.dto.VerifyEmailRequest;
import com._p1m.productivity_suite.security.service.AuthService;
import com._p1m.productivity_suite.security.service.JwtService;
import com._p1m.productivity_suite.security.utils.AuthUtil;
import com._p1m.productivity_suite.security.utils.OtpUtils;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final UserUtil userUtil;
    private final AuthUtil authUtil;
    private final EmailService emailService;

    private final Map<String, OtpUtils.OtpData> otpStore = new ConcurrentHashMap<>();
    private String emailInProcess;

    @Override
    public ApiResponse authenticateUser(final LoginRequest loginRequest, final String routeName, final String browserName, String pageName) {
        final String identifier = loginRequest.getEmail();
        log.info("Authenticating user with identifier: {}", identifier);

        final Optional<User> userOpt = this.userRepository.findByEmail(identifier)
                .or(() -> this.userRepository.findByUsername(identifier));

        final User user = userOpt.orElseThrow(() -> {
            log.warn("User not found with identifier: {}", identifier);
            return new UnauthorizedException("Invalid email/username or password");
        });

        if (!user.isStatus()) {
            log.warn("User is inactive: {}", loginRequest.getEmail());
            return ApiResponse.builder()
                    .success(0)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Your account has been locked. Please contact your administrator.")
                    .build();
        }

        if (!this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user: {}", loginRequest.getEmail());
            return ApiResponse.builder()
                    .success(0)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid email or password")
                    .build();
        }

        log.info("User authenticated successfully: {}", loginRequest.getEmail());

        boolean firstTimeLogin = false;

        if(user.isLoginFirstTime()) {
            firstTimeLogin = true;
            user.setLoginFirstTime(false);
            this.userRepository.save(user);
            log.info("User {} logged in for the first time.", user.getName());
        }

        final UserDto userDto = DtoUtil.map(user, UserDto.class, modelMapper);
        userDto.setLoginFirstTime(firstTimeLogin);
        userDto.setGenderId(Gender.fromInt(user.getGender()).getValue());
        userDto.setGenderName(Gender.fromInt(user.getGender()).getCode());

        Map<String, Object> tokenData = authUtil.generateTokens(user);

        return ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(
                        Map.of(
                        "currentUser", userDto,
                        "accessToken", tokenData.get("accessToken")
                        )
                )
                .message("You are successfully logged in!")
                .build();
    }

    @Override
    @Transactional
    public ApiResponse registerUser(final RegisterRequest registerRequest) {
        log.info("Registering new user with email: {}", registerRequest.getEmail());

        if (this.userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            log.warn("Email already exists: {}", registerRequest.getEmail());
            return ApiResponse.builder()
                    .success(0)
                    .code(HttpStatus.CONFLICT.value())
                    .message("Email is already in use")
                    .build();
        }

        final User newUser = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(this.passwordEncoder.encode(registerRequest.getPassword()))
                .gender(registerRequest.getGender())
                .emailVerified(false)
                .build();

        this.userRepository.save(newUser);

        final Map<String, Object> tokenData = this.authUtil.generateTokens(newUser);

        final String accessToken = (String) tokenData.get("accessToken");

        log.info("User registered successfully: {}", registerRequest.getEmail());

        final UserDto userDto = DtoUtil.map(newUser, UserDto.class, modelMapper);

        final String token = UUID.randomUUID().toString();
        final VerifyEmailRequest emailRequest = new VerifyEmailRequest(newUser.getEmail(), token);
        this.emailService.sendVerifyEmail(emailRequest);

        return ApiResponse.builder()
                .success(1)
                .code(HttpStatus.CREATED.value())
                .data(
                        Map.of(
                        "user", userDto,
                        "accessToken", accessToken
                        )
                )
                .message("You have registered successfully.")
                .build();
    }

    @Override
    public void logout(final String accessToken) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            final String token = accessToken.substring(7);
            final Claims claims = this.jwtService.validateToken(token);
            final String userEmail = claims.getSubject();

            final User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UnauthorizedException(
                            "User not found. Cannot proceed with logout."));

            log.debug("Revoking access token for user: {}", user.getEmail());
            this.jwtService.revokeToken(token);
        }

        log.info("User successfully logged out.");
    }

    @Override
    public ApiResponse getCurrentUser(final String authHeader, final String routeName, final String browserName,
                                      final String pageName) {
        final UserDto userDto = userUtil.getCurrentUserDto(authHeader);
        EntityUtil.getEntityById(userRepository, userDto.getId());

        return ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(Map.of(
                        "user", userDto)
                )
                .message("User retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse changePassword(String email) {
        log.info("Initiating password reset for email: {}", email);

        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("No user found with email: {}", email);
                    return new UnauthorizedException("No user found with this email");
                });

        final String otp = OtpUtils.generateOtp();
        otpStore.put(otp, new OtpUtils.OtpData(email, Instant.now().plus(30, ChronoUnit.MINUTES)));

        try {
            return ApiResponse.builder()
                    .success(1)
                    .code(HttpStatus.OK.value())
                    .data(Map.of(
                            "otp", otp)
                    )
                    .message("OTP has been sent to your email")
                    .build();
        } catch (Exception e) {
            log.error("Failed to send OTP email: {}", e.getMessage());
            throw new RuntimeException("Failed to send OTP");
        }
    }

    @Override
    public ApiResponse verifyOtp(final String otp) {
        log.info("Verifying OTP");

        final OtpUtils.OtpData otpData = otpStore.get(otp);
        if (otpData == null || otpData.isExpired()) {
            log.warn("Invalid or expired OTP");
            throw new UnauthorizedException("Invalid or expired OTP");
        }

        emailInProcess = otpData.getEmail();
        otpStore.remove(otp);

        return ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(true)
                .message("OTP verified successfully")
                .build();
    }

    @Override
    public ApiResponse resetPassword(final ResetPasswordRequest resetPasswordRequest) {
        if (this.emailInProcess == null) {
            throw new UnauthorizedException("Please verify OTP first");
        }

        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            throw new UnauthorizedException("Passwords do not match");
        }

        final User user = this.userRepository.findByEmail(emailInProcess)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        user.setPassword(this.passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        this.userRepository.save(user);

        this.emailInProcess = null;

        return ApiResponse.builder()
                .success(1)
                .code(HttpStatus.OK.value())
                .data(true)
                .message("Password reset successfully")
                .build();
    }

}