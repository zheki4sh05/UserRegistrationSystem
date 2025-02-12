package com.example.UserRegistrationSystem.config;

import com.example.UserRegistrationSystem.service.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;

@Configuration
public class ApplicationConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
