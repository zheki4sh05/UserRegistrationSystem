package com.example.UserRegistrationSystem.service;

import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.model.*;

import java.util.*;

public interface IAuthenticationService {

    void register(SignUpDto authDto);

    AuthenticationResponse authenticate(AuthDto request);

    User checkPassword(String password, Optional<User> isExist);


}
