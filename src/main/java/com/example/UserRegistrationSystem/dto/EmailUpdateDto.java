package com.example.UserRegistrationSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
public class EmailUpdateDto {
    @Email
    private String email;

    @NotBlank
    private String password;
}
