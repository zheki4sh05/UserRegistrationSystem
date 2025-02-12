package com.example.UserRegistrationSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.*;

import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @NotBlank
    @Size(min = 4, max = 20)
    private String firstname;

    @NotBlank
    @Size(min = 4, max = 20)
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 12)
    private String password;

    @DateTimeFormat
    private LocalDate birthdate;


}
