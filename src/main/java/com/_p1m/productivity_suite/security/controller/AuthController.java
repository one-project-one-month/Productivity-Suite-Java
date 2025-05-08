package com._p1m.productivity_suite.security.controller;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.security.dto.*;
import com._p1m.productivity_suite.security.service.AuthService;
import com._p1m.productivity_suite.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Module", description = "Endpoints for user authentication, registration, and password management")
@RestController
@RequestMapping("/productivity-suite/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    public final JwtService jwtService;

    @Operation(
            summary = "Login a user",
            description = "Authenticates a user using email and password, and returns authentication tokens.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @Validated @RequestBody final LoginRequest loginRequest,
            final HttpServletRequest request,
            @RequestParam(required = false) final String routeName,
            @RequestParam(required = false) final String browserName,
            @RequestParam(required = false) final String pageName
    ) {
        log.info("Received login attempt for email: {}", loginRequest.getEmail());

        double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final ApiResponse response = this.authService.authenticateUser(loginRequest, routeName, browserName, pageName);

        if (response.getSuccess() == 1) {
            log.info("Login successful for user: {}", loginRequest.getEmail());
        } else {
            log.warn("Login failed for user: {}", loginRequest.getEmail());
        }

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @Operation(
            summary = "Logout a user",
            description = "Logs out the current user by invalidating their session and tokens.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Logout successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @RequestHeader(value = "Authorization", required = false) final String accessToken,
            final HttpServletRequest request
    ) {
        log.info("Received logout request");

        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        if ((accessToken == null || !accessToken.startsWith("Bearer "))) {

            log.warn("Invalid or missing tokens in logout request");
            throw new UnauthorizedException("Invalid or missing authorization tokens.");
        }

        try {
            this.authService.logout(accessToken);
            final ApiResponse response = ApiResponse.builder()
                    .success(1)
                    .code(200)
                    .data(true)
                    .message("Logout successful")
                    .build();

            log.info("User logged out successfully");

            return ResponseUtils.buildResponse(request, response, requestStartTime);
        } catch (UnauthorizedException ex) {
            log.warn("Logout failed due to security reasons: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during logout", ex);
            throw new RuntimeException("An error occurred during logout.");
        }
    }

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with the provided registration details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/register")
//    @DeprecatedRoute(message = "This endpoint is deprecated. Use /new-endpoint instead.")
    public ResponseEntity<ApiResponse> register(@Validated @RequestBody final RegisterRequest registerRequest,
            final HttpServletRequest request) {
        log.info("Received registration request for email: {}", registerRequest.getEmail());

        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final ApiResponse response = this.authService.registerUser(registerRequest);

        if (response.getSuccess() == 1) {
            log.info("User registered successfully: {}", registerRequest.getEmail());
        } else {
            log.warn("Registration failed for email: {}", registerRequest.getEmail());
        }

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @Operation(
            summary = "Get the current authenticated user",
            description = "Fetches the current authenticated user's details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Current user details",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser(
            @RequestHeader("Authorization") final String authHeader,
            @RequestParam(required = false) final String routeName,
            @RequestParam(required = false) final String browserName,
            @RequestParam(required = false) final String pageName,
            HttpServletRequest request) {
        log.info("Fetching current authenticated user");

        final double requestStartTime = System.currentTimeMillis();
        final ApiResponse response = this.authService.getCurrentUser(authHeader, routeName, browserName, pageName);

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @Operation(
            summary = "Change password for a user",
            description = "Changes the password for the user based on the provided email.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password change successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> forgotPassword(
            @Validated @RequestBody final ChangePasswordRequest changePasswordRequest,
            HttpServletRequest httpRequest) {
        log.info("Received change password request");
        final double requestStartTime = RequestUtils.extractRequestStartTime(httpRequest);

        final ApiResponse response = this.authService.changePassword(changePasswordRequest.getEmail());
        return ResponseUtils.buildResponse(httpRequest, response, requestStartTime);
    }

    @Operation(
            summary = "Verify OTP for user",
            description = "Verifies the OTP (One-Time Password) provided for a user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP verification successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(
            @Validated @RequestBody final VerifyOtpRequest verifyOtpRequest,
            HttpServletRequest httpRequest) {
        log.info("Received OTP verification request");
        double requestStartTime = RequestUtils.extractRequestStartTime(httpRequest);

        final ApiResponse response = this.authService.verifyOtp(verifyOtpRequest.getOtp());
        return ResponseUtils.buildResponse(httpRequest, response, requestStartTime);
    }

    @Operation(
            summary = "Reset password for a user",
            description = "Resets the password for the user based on the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password reset successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @Validated @RequestBody final ResetPasswordRequest resetPasswordRequest,
            final HttpServletRequest httpRequest) {
        log.info("Received password reset request");
        final double requestStartTime = RequestUtils.extractRequestStartTime(httpRequest);

        final ApiResponse response = this.authService.resetPassword(resetPasswordRequest);
        return ResponseUtils.buildResponse(httpRequest, response, requestStartTime);
    }
}
