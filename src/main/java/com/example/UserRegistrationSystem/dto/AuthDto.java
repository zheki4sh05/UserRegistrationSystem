package com.example.UserRegistrationSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthDto {
    private String email;

    @NotBlank(message = "PASS_EMPTY")
    @NotEmpty(message = "PASS_EMPTY")
    @Size(min = 6, max = 12,message = "PASS_INCORRECT")
    private String password;
}
