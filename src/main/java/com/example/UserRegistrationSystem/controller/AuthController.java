package com.example.UserRegistrationSystem.controller;


import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.service.impl.*;
import com.example.UserRegistrationSystem.util.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/auth")
@Controller
@Slf4j
public class AuthController {


    private final AuthenticationServiceImpl authenticationService;

    private final HttpResponseUtils httpResponseUtils;
    private final HttpRequestUtils httpRequestUtils;

    @GetMapping("/login")
    public String login(
    ){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(
    ){
        return "signup";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("auth") AuthDto authDto,
            HttpServletResponse httpServletResponse
            ){
       AuthenticationResponse authenticationResponse =  authenticationService.authenticate(authDto);
       Cookie[] cookies = httpResponseUtils.createCookie(authenticationResponse);
       httpServletResponse.addCookie(cookies[0]);
       httpServletResponse.addCookie(cookies[1]);
       return "profile";
    }


    @PostMapping("/signup")
    public String signup(
           @Valid @ModelAttribute("signup") SignUpDto signUpDto,
            BindingResult bindingResult,
            HttpServletResponse httpServletResponse
    ){
        AuthenticationResponse authenticationResponse =  authenticationService.register(signUpDto);
        Cookie[] cookies = httpResponseUtils.createCookie(authenticationResponse);
        httpServletResponse.addCookie(cookies[0]);
        httpServletResponse.addCookie(cookies[1]);
        return "profile";
    }






}
