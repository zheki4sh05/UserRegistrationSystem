package com.example.UserRegistrationSystem.filters;

import com.example.UserRegistrationSystem.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class RedirectFilter extends OncePerRequestFilter {

    private final HttpResponseUtils httpResponseUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("auth") || request.getServletPath().equals("/")) {
            if(httpResponseUtils.hasCookie(request)){
                response.sendRedirect("/profile");
                return;
            }
        }
        if(request.getServletPath().contains("profile")){
            if(!httpResponseUtils.hasCookie(request)){
                response.sendRedirect("/auth/login");
                return;
            }
        }
        filterChain.doFilter(request, response);


    }
}
