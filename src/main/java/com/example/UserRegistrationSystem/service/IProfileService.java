package com.example.UserRegistrationSystem.service;

import com.example.UserRegistrationSystem.dto.*;

public interface IProfileService {
    UserDto findById(String userId);

    UserDto update(UserDto userDto);

    void deleteById(String userId);
}
