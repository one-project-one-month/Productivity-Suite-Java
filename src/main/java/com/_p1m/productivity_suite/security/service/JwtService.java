package com._p1m.productivity_suite.security.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {
    Claims validateToken(final String token);

    void revokeToken(final String token);

    String generateToken(final Map<String, Object> claims, final String subject, final long expirationMillis);
}