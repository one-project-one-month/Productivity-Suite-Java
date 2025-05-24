package com._p1m.productivity_suite.security.dto;

import com._p1m.productivity_suite.config.annotations.ValidGender;
import com._p1m.productivity_suite.config.annotations.ValidName;
import com._p1m.productivity_suite.config.annotations.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @ValidName
    private String name;

    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @ValidPassword
    private String password;

    @ValidGender
    private Integer gender;
}