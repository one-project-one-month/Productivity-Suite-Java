package com._p1m.productivity_suite.features.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private boolean status;
    private Integer genderId;
    private String genderName;
    private boolean loginFirstTime;
    private String createdAt;
    private String updatedAt;
}
