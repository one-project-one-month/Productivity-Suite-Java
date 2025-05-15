package com._p1m.productivity_suite.security.service.normal;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;

public interface JwtService {
    Claims validateToken(final String token);

    void revokeToken(final String token);

    String generateToken(final Map<String, Object> claims, final String subject, final long expirationMillis);
    
    UsernamePasswordAuthenticationToken getAuthentication(String token);

}