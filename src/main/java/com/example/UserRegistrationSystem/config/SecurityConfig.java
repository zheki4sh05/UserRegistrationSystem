package com.example.UserRegistrationSystem.config;


import com.example.UserRegistrationSystem.filters.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter;
    private final CustomLogoutHandler logoutHandler;
    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         http
                 .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(authorizeRequests->
                         authorizeRequests
                                 .requestMatchers("/").permitAll()
                                 .requestMatchers("/auth").permitAll()
                                 .requestMatchers("/auth/*").permitAll()
                                 .anyRequest().authenticated()
                 )
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authenticationProvider(authenticationProvider(userDetailsService))
                 .addFilterBefore(jwtCookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(401)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l->l
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ));

        return http.build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }






}
