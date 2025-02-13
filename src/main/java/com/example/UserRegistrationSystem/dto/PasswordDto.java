package com.example.UserRegistrationSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public Boolean isSimilar(){
        return newPassword.equals(confirmPassword);
    }
}
