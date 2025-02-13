package com.example.UserRegistrationSystem.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.*;

import java.time.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private String id;
    @NotBlank
    @Size(min = 4, max = 20, message = "firstname length must be from 4 to 20 ")
    private String firstname;

    @NotBlank
    @Size(min = 4, max = 20, message = "lastname length must be from 4 to 20 ")
    private String lastname;

    @NotBlank
    @Email(message = "email not valid")
    private String email;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

}
