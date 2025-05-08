package com._p1m.productivity_suite.security.utils;

import lombok.Value;

import java.time.Instant;
import java.util.Random;

public class OtpUtils {

    public static String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Value
    public static class OtpData {
        String email;
        Instant expiration;

        public boolean isExpired() {
            return Instant.now().isAfter(expiration);
        }
    }
}