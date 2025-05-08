package com._p1m.productivity_suite.features.users.utils;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.security.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserUtil {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserUtil(final JwtService jwtService, final UserRepository userRepository, final ModelMapper modelMapper) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto getCurrentUserDto(final String authHeader) {
        final String email = this.extractEmailFromToken(authHeader);
        final User user = this.findUserByEmail(email);
        return this.modelMapper.map(user, UserDto.class);
    }

    public String extractEmailFromToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Invalid Authorization header received");
            throw new UnauthorizedException("Unauthorized: Missing or invalid token");
        }

        final String token = authHeader.substring(7);
        final Claims claims = this.jwtService.validateToken(token);
        return claims.getSubject();
    }


    public User findUserByEmail(final String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found for email: {}", email);
                    return new UnauthorizedException("User not found");
                });
    }
}
