package com.example.UserRegistrationSystem.util;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class HttpRequestUtils {
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
