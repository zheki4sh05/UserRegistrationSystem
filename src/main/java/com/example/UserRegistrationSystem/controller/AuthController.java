package com.example.UserRegistrationSystem.controller;


import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.service.*;
import com.example.UserRegistrationSystem.util.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@AllArgsConstructor
@RequestMapping("/auth")
@Controller
@Slf4j
public class AuthController {

    private final IAuthenticationService authenticationService;
    private final HttpResponseUtils httpResponseUtils;

    @GetMapping("")
    public String welcome(
            Model model
    ){
        model.addAttribute("auth", false);
        return "login";
    }

    @GetMapping("/login")
    public String login(
            Model model
    ){
        model.addAttribute("auth", false);
        return "login";
    }

    @GetMapping("/signup")
    public String signup(
            Model model
            ){
        model.addAttribute("auth", false);
        return "signup";
    }

    @PostMapping("/login")
    public ModelAndView login(
            @ModelAttribute("login") AuthDto authDto,
            HttpServletResponse httpServletResponse
            ){
       AuthenticationResponse authenticationResponse =  authenticationService.authenticate(authDto);
       Cookie[] cookies = httpResponseUtils.createCookie(authenticationResponse);
       httpServletResponse.addCookie(cookies[0]);
       httpServletResponse.addCookie(cookies[1]);

        ModelAndView modelAndView = new ModelAndView("redirect:/profile");
        modelAndView.addObject("id", authenticationResponse.getUserDto().getId());
       return modelAndView;
    }


    @PostMapping("/signup")
    public String signup(
           @Valid @ModelAttribute("signup") SignUpDto signUpDto,
            BindingResult bindingResult,
            HttpServletResponse httpServletResponse
    ){
        AuthenticationResponse authenticationResponse =  authenticationService.register(signUpDto);
        return "redirect:/auth/login";
    }






}
