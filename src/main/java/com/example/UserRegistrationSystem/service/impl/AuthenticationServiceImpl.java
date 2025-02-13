package com.example.UserRegistrationSystem.service.impl;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.exceptions.*;
import com.example.UserRegistrationSystem.mapper.*;
import com.example.UserRegistrationSystem.model.*;
import com.example.UserRegistrationSystem.service.*;
import com.example.UserRegistrationSystem.util.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService{
    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;

    @Transactional
    public void register(SignUpDto authDto) {

        Optional<User> isExist = repository.findByEmail(authDto.getEmail());
        if(isExist.isPresent()) {
            throw new EntityAlreadyExists("user with such email already exists");
        }
        var user  = userMapper.mapFrom(authDto);
        repository.save(user);
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthDto authDto) {
        Optional<User> userOptional = repository.findByEmail(authDto.getEmail());
        User user = checkPassword(authDto.getPassword(), userOptional);
        String accessToken = jwtService.generateAccessToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(accessToken, user);
        return new AuthenticationResponse
                (accessToken, userMapper.toDto(user));



    }
    public User checkPassword(String password, Optional<User> isExist){
        String message = "invalid user email or password";
        if(isExist.isPresent()){
            if (new PasswordEncoderWrapper().matches(password, isExist.get().getPassword())) {
                return isExist.get();
            } else {
                throw new InvalidCredentialsException(message);
            }
        }else{
            throw new InvalidCredentialsException(message);
        }

    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }
        tokenRepository.deleteAll(validTokens);
    }
    private void saveUserToken(String accessToken, User user) {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setUser(user);
        token.setAccessToken(accessToken);
        tokenRepository.save(token);
    }

}
