package com._p1m.productivity_suite.security.dto;

import com._p1m.productivity_suite.config.annotations.ValidPassword;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {

    @ValidPassword(fieldName = "New password")
    private String newPassword;

    @ValidPassword(fieldName = "Confirm password")
    private String confirmPassword;
}