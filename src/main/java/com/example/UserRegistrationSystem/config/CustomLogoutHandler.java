package com.example.UserRegistrationSystem.config;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.model.*;
import com.example.UserRegistrationSystem.util.*;
import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;

import java.io.*;

@Configuration
@AllArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final HttpResponseUtils httpResponseUtils;
    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        if(request.getCookies()!=null){
            String accessToken=null;
            String refreshToken=null;
            var cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (httpResponseUtils.accessCookie().equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    response.addCookie(httpResponseUtils.deleteCookie(cookie.getName()));
                }
                if (httpResponseUtils.refreshCookie().equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    response.addCookie(httpResponseUtils.deleteCookie(cookie.getName()));
                }
            }
            Token storedToken = tokenRepository.findByAccessToken(accessToken).orElse(null);
            var tokens = tokenRepository.findAllAccessTokensByUser(storedToken.getUser().getId());
            tokenRepository.deleteAll(tokens);
            try {
                response.sendRedirect("/auth/login");
            } catch (IOException e) {
              log.error("logout handler {}", e.getMessage());
            }
        }



    }
}
