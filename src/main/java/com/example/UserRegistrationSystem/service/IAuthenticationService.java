package com.example.UserRegistrationSystem.service;

import com.example.UserRegistrationSystem.dto.*;

public interface IAuthenticationService {

    void register(SignUpDto authDto);

    AuthenticationResponse authenticate(AuthDto request);


}
