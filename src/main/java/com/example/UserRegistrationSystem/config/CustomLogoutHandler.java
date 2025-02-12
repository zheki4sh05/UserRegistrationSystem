package com.example.UserRegistrationSystem.config;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.model.*;
import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;

@Configuration
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);
        var tokens = tokenRepository.findAllAccessTokensByUser(storedToken.getUser().getUuid());
        tokenRepository.deleteAll(tokens);

    }
}
