package com.example.UserRegistrationSystem.service;

import com.example.UserRegistrationSystem.dto.*;

public interface IProfileService {
    UserDto findById(String userId);

    UserDto update(UserDto userDto);

    UserDto updateEmail(EmailUpdateDto emailUpdateDto,String userId);

    UserDto updatePassword(PasswordDto passwordDto,String userId);
}
