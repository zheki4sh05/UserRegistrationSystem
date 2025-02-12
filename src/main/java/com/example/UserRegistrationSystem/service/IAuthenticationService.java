package com.example.UserRegistrationSystem.service;

import com.example.UserRegistrationSystem.dto.*;

public interface IAuthenticationService {

    AuthenticationResponse register(SignUpDto authDto);

    AuthenticationResponse authenticate(AuthDto request);

    AuthenticationResponse refreshToken();

}
