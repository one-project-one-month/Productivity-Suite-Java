package com._p1m.productivity_suite.security.service;

import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.security.dto.LoginRequest;
import com._p1m.productivity_suite.security.dto.RegisterRequest;
import com._p1m.productivity_suite.security.dto.ResetPasswordRequest;

public interface AuthService {
    ApiResponse authenticateUser(final LoginRequest loginRequest, final String routeName, String browserName, String pageName);

    ApiResponse registerUser(final RegisterRequest registerRequest);

    void logout(final String accessToken);

    ApiResponse getCurrentUser(final String authHeader, final String routeName, final String browserName, final String pageName);

    ApiResponse changePassword(final String email);

    ApiResponse verifyOtp(final String otp);

    ApiResponse resetPassword(final ResetPasswordRequest resetPasswordRequest);
}
