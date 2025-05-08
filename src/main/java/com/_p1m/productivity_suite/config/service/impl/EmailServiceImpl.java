package com._p1m.productivity_suite.config.service.impl;

import com._p1m.productivity_suite.config.service.EmailService;
import com._p1m.productivity_suite.security.dto.VerifyEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Override
    public boolean sendVerifyEmail(final VerifyEmailRequest request) {
        try {
            final boolean result = mockSendVerifyEmail(request);
            if (!result) {
                throw new RuntimeException("Failed to send verification email to " + request.getEmail());
            }

            log.info("Verification email sent to: {}", request.getEmail());
            log.info("Verification link: https://productivity-suite.com/verify?token={}", request.getVerificationToken());
            
            return true;
        } catch (Exception e) {
            log.error("SendVerifyEmail failed: {}", e.getMessage(), e);
            throw new RuntimeException("SendVerifyEmail failed: " + e.getMessage(), e);
        }
    }

    private boolean mockSendVerifyEmail(VerifyEmailRequest request) { return true; }
}
