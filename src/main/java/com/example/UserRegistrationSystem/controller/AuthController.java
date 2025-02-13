package com.example.UserRegistrationSystem.controller;


import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.exceptions.*;
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
import org.springframework.web.servlet.mvc.support.*;

import java.util.stream.*;

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
        AuthDto authDto = new AuthDto();
        model.addAttribute("auth", false);
        model.addAttribute("authDto",authDto);
        model.addAttribute("error", "");
        return "login";
    }

    @GetMapping("/login")
    public String login(
            Model model
    ){
        AuthDto authDto = new AuthDto();
        model.addAttribute("auth", false);
        model.addAttribute("authDto", authDto);
        model.addAttribute("error", "");
        return "login";
    }

    @GetMapping("/signup")
    public String signup(
            Model model
            ){
        SignUpDto signUpDto = new SignUpDto();
        model.addAttribute("auth", false);
        model.addAttribute("signUpDto", signUpDto);
        model.addAttribute("error", "");
        return "signup";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("login") AuthDto authDto,
            HttpServletResponse httpServletResponse,
            RedirectAttributes redirectAttributes,
            Model model
            ){
        try{
            AuthenticationResponse authenticationResponse =  authenticationService.authenticate(authDto);
            Cookie cookie = httpResponseUtils.createCookie(authenticationResponse);
            httpServletResponse.addCookie(cookie);
            redirectAttributes.addAttribute("id", authenticationResponse.getUserDto().getId());
            return "redirect:/profile";
        }catch (InvalidCredentialsException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("authDto", authDto);
            return "login";
        }

    }


    @PostMapping("/signup")
    public String signup(
           @Valid @ModelAttribute("signup") SignUpDto signUpDto,
           Model model,
           BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("error",
                    bindingResult
                    .getFieldErrors()
                            .stream()
                            .map(e->e.getDefaultMessage())
                            .collect(Collectors.joining(",")));
                    ;
            model.addAttribute("signUpDto", signUpDto);
            return "signup";
        }else{
            try{
                authenticationService.register(signUpDto);
                return "redirect:/auth/login";
            }catch (EntityAlreadyExists e){
                model.addAttribute("error", e.getMessage());
                model.addAttribute("signUpDto", signUpDto);
                return "signup";
            }
        }
    }






}
