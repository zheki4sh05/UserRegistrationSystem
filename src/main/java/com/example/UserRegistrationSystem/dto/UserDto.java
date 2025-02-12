package com.example.UserRegistrationSystem.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserDto {
    private String id;

    @NotBlank(message ="LOGIN_EMPTY")
    private String firstname;

    @NotBlank(message ="LOGIN_EMPTY")
    private String lastname;

    @Email(message = "EMAIL_INCORRECT")
    private String email;

    @NotBlank
    private LocalDate birthdate;

    @NotBlank(message = "PASS_EMPTY")
    @NotEmpty(message = "PASS_EMPTY")
    @Size( min = 6,message = "PASS_INCORRECT", max = 12)
    private String password;

}
