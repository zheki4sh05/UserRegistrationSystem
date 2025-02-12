package com.example.UserRegistrationSystem.util;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.model.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;


@Service("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    @Autowired
    private UserRepository userRepository;

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        var principal =(User) authentication.getPrincipal();
        return principal;
    }

    public String getUserId(){
        return getPrincipal().getUuid().toString();
    }


}
