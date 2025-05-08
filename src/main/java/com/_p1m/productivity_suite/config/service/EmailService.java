package com._p1m.productivity_suite.config.service;

import com._p1m.productivity_suite.security.dto.VerifyEmailRequest;

public interface EmailService {
    boolean sendVerifyEmail(final VerifyEmailRequest request);
}
