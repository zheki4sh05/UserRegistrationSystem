package com.example.UserRegistrationSystem.service.impl;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.exceptions.*;
import com.example.UserRegistrationSystem.mapper.*;
import com.example.UserRegistrationSystem.model.*;
import com.example.UserRegistrationSystem.service.*;
import jakarta.persistence.*;
import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    @Transactional
    public AuthenticationResponse register(SignUpDto authDto) {


        Optional<User> isExist = repository.findByEmailOrLogin(authDto.getEmail());
        if(isExist.isPresent()) {
            throw new EntityAlreadyExists();
        }
        var user  = userMapper.mapFrom(authDto);
        user = repository.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful",null);

    }

    @Transactional
    public AuthenticationResponse authenticate(AuthDto request) {


        User user = repository.getByEmailOrLogin(request.getLogin());
        UserDto userDto = userMapper.toDto(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse
                (accessToken, refreshToken, "User login was successful", userDto);

    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUuid());
        if(validTokens.isEmpty()) {
            return;
        }
        tokenRepository.deleteAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    @Transactional
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = repository.findByEmailOrLogin(username)
                .orElseThrow(()->new EntityNotFoundException());

        if(jwtService.isValidRefreshToken(token, user)) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);
            UserDto userDto = userMapper.toDto(user);
            return new ResponseEntity<>(new AuthenticationResponse(accessToken, refreshToken, "New token generated",userDto), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
