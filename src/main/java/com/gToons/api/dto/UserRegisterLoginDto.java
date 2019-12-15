package com.gToons.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRegisterLoginDto {
    private String username;
    private String email;
    private String password;
}
