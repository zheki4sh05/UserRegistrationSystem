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
               Boolean result =  securityService.check(username, accessToken, request);
               if(result){
                   filterChain.doFilter(request, response);
               }else{
                   response.sendRedirect("/auth/logout");
               }
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

}
