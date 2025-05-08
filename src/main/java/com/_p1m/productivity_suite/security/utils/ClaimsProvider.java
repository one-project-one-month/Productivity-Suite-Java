package com._p1m.productivity_suite.security.utils;

import com._p1m.productivity_suite.data.models.User;

import java.util.HashMap;
import java.util.Map;

public class ClaimsProvider {

    private ClaimsProvider() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, Object> generateClaims(final User user) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        return claims;
    }
}
