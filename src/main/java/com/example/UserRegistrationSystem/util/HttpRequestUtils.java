package com.example.UserRegistrationSystem.util;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class HttpRequestUtils {

    @Value("${client.hostName}")
    private String host;

    @Value("${client.port}")
    private String port;

    public HttpRequestUtils() {
    }

    public HttpRequestUtils(String host, String port) {
        this.host = host;
        this.port = port;
    }


    public Boolean isPublic(HttpServletRequest httpRequest){
        var path = httpRequest.getServletPath();
        for (PublicPath p : PublicPath.values()) {
            if (path.contains(p.type())) {
                return true;
            }
        }
        return false;
    }




}
