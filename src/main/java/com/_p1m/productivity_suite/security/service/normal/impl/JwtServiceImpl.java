package com._p1m.productivity_suite.security.service.normal.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.exceptions.TokenExpiredException;
import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
import com._p1m.productivity_suite.security.service.normal.JwtService;
import com._p1m.productivity_suite.security.utils.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class JwtServiceImpl implements JwtService {

    private final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();

    @Override
    public Claims validateToken(final String token) {
        if (!JwtUtil.isTokenValid(token)) {
            throw new TokenExpiredException("Invalid or expired token.");
        }

        if (this.isTokenRevoked(token)) {
            throw new UnauthorizedException("Token has been revoked.");
        }

        return JwtUtil.decodeToken(token);
    }

    @Override
    public void revokeToken(final String token) {
        this.revokedTokens.add(token);
    }

    private boolean isTokenRevoked(final String token) {
        return this.revokedTokens.contains(token);
    }

    @Override
    public String generateToken(final Map<String, Object> claims, final String subject, final long expirationMillis) {
        return JwtUtil.generateToken(claims, subject, expirationMillis);
    }
    
    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = validateToken(token);
        String username = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(
            username, null, null
        );
    }

}