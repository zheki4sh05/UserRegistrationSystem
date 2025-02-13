package com.example.UserRegistrationSystem.filters;

import com.example.UserRegistrationSystem.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

//@Component
//@RequiredArgsConstructor
//public class JwtCookieAccessFilter extends OncePerRequestFilter {
//
//    private final HttpRequestUtils httpRequestUtils;
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        if(httpRequestUtils.isPublic(request)){
//            response.sendRedirect("/profile");
//        }else{
//            filterChain.doFilter(request, response);
//        }
//
//    }
//}
