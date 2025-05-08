package com._p1m.productivity_suite.security.dto;

import com._p1m.productivity_suite.config.annotations.ValidOtp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpRequest {

    @ValidOtp
    private String otp;
}
