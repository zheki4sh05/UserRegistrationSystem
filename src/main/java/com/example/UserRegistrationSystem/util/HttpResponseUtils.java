package com.example.UserRegistrationSystem.util;

import com.example.UserRegistrationSystem.dto.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class HttpResponseUtils {

    @Value("${application.security.jwt.accessTokenExpire}")
    private Long accessTokenExpire;


    @Value("${spring.application.name}")
    private String applicationName;

    public HttpResponseUtils(){}

    public HttpResponseUtils(Long accessTokenExpire, String applicationName) {
        this.accessTokenExpire = accessTokenExpire;
        this.applicationName = applicationName;
    }

    public String accessCookie(){
        return applicationName+"_access";
    }

    public Cookie createCookie(AuthenticationResponse authenticationResponse) {
        return createTokenCookie(accessCookie(),authenticationResponse.getAccessToken(), accessTokenExpire);
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
    public Boolean hasCookie(HttpServletRequest httpServletRequest){
        return httpServletRequest.getCookies()!=null && Arrays.stream(httpServletRequest.getCookies())
                .anyMatch(cookie -> cookie.getName().equals(accessCookie()));
    }

}
