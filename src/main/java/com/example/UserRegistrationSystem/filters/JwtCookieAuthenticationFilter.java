package com.example.UserRegistrationSystem.filters;

import com.example.UserRegistrationSystem.service.*;
import com.example.UserRegistrationSystem.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final HttpResponseUtils httpResponseUtils;
    private final SecurityService securityService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {


        if (request.getServletPath().contains("auth") || request.getServletPath().equals("/")) {
            if(httpResponseUtils.hasCookie(request)){
                response.sendRedirect("/profile");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getCookies()!=null){
            String accessToken=null;
            var cookies = request.getCookies();
            for(int i=0;i<cookies.length; i++){
                if(httpResponseUtils.accessCookie().equals(cookies[i].getName())){
                    accessToken = cookies[i].getValue();
                }
            }
            if(accessToken!=null){
                String username = jwtService.extractUsername(accessToken);
                securityService.check(username, accessToken, request);
                filterChain.doFilter(request, response);
            }
        }else{
            filterChain.doFilter(request, response);
        }




    }

}
