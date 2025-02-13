package com.example.UserRegistrationSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthDto {
    private String email;
    private String password;
}
