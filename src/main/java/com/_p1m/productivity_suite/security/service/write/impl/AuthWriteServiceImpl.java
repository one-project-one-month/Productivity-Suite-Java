package com._p1m.productivity_suite.security.service.write.impl;

import com._p1m.productivity_suite.config.command.CommandProcessingResult;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.security.dto.LoginRequest;
import com._p1m.productivity_suite.security.service.normal.AuthService;
import com._p1m.productivity_suite.security.service.write.AuthWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthWriteServiceImpl implements AuthWriteService {

    private final AuthService authService;

    @Override
    public CommandProcessingResult login(final LoginRequest request) {
        final ApiResponse response = this.authService.authenticateUser(request);

        if (response.getSuccess() == 1) {
            Long userId = null;

            if (response.getData() instanceof java.util.Map<?, ?> map && map.get("currentUser") instanceof UserDto userDto) {
                userId = userDto.getId();
            }

            return CommandProcessingResult.success(userId, "Login successful.", response.getData());
        }

        return CommandProcessingResult.failure("Login failed: " + response.getMessage());
    }
}
