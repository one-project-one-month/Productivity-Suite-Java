package com._p1m.productivity_suite.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;
}
