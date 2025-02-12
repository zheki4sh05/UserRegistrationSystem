package com.example.UserRegistrationSystem.util;

import com.example.UserRegistrationSystem.dto.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class HttpResponseUtils {

    @Value("${application.security.jwt.accessTokenExpire}")
    private Long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpire;

    @Value("${spring.application.name}")
    private String applicationName;

    public HttpResponseUtils(){}

    public HttpResponseUtils(Long accessTokenExpire, Long refreshTokenExpire, String applicationName) {
        this.accessTokenExpire = accessTokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.applicationName = applicationName;
    }

    public String accessCookie(){
        return applicationName+"_access";
    }
    public String refreshCookie(){
        return applicationName+"_refresh";
    }

    public Cookie[] createCookie(AuthenticationResponse authenticationResponse) {
       Cookie[] cookies = new Cookie[2];
       cookies[0] = createTokenCookie(accessCookie(),authenticationResponse.getAccessToken(), accessTokenExpire);
        cookies[1] = createTokenCookie(refreshCookie(),authenticationResponse.getRefreshToken(), refreshTokenExpire);
        return cookies;
    }
    private Cookie createTokenCookie(String name, String value, Long expired){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(expired.intValue());
        return cookie;
    }

    public Cookie deleteCookie(String name) {
        Cookie cookie = new Cookie(name,null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
