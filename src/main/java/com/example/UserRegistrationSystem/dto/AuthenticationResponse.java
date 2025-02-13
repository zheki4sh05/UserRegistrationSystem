package com.example.UserRegistrationSystem.dto;

import com.example.UserRegistrationSystem.dto.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    private UserDto userDto;
}
