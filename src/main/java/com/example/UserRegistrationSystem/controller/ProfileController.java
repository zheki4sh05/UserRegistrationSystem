package com.example.UserRegistrationSystem.controller;

import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.exceptions.*;
import com.example.UserRegistrationSystem.service.*;
import com.example.UserRegistrationSystem.util.*;
import io.jsonwebtoken.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import java.util.stream.*;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final IProfileService profileService;
    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping("")
   public String getUserProfile(
            @RequestParam(value = "id",required = false) String userId,
            Model model
    ){
        if(userId==null){
            userId = customSecurityExpression.getUserId();
        }
        UserDto userDto = profileService.findById(userId);
        model.addAttribute("user", userDto);
        model.addAttribute("auth", true);
        model.addAttribute("error", "");
        return "profile";
    }


    @PostMapping("")
    public String updateUserProfile(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("error",
                    bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(e->e.getDefaultMessage())
                            .collect(Collectors.joining(",")));

        }else{
            try{
                userDto = profileService.update(userDto);
                model.addAttribute("error", "");
            }catch (EntityAlreadyExists | InvalidCredentialsException e) {
                model.addAttribute("error", e.getMessage());
            }

        }
        model.addAttribute("user", userDto);
        model.addAttribute("auth", true);
        model.addAttribute("user", userDto);
        return "profile";
    }

    @PostMapping("/email")
    public String updateEmail(
            @Valid @ModelAttribute("emailDto") EmailUpdateDto emailUpdateDto,
            Model model
    ){
        try{
           profileService.updateEmail(emailUpdateDto, customSecurityExpression.getUserId());
            return "redirect:/auth/logout";
        }catch (EntityAlreadyExists e){
            UserDto userDto = profileService.findById(customSecurityExpression.getUserId());
            model.addAttribute("user", userDto);
            model.addAttribute("error", e.getMessage());
            return "/profile";
        }
    }
    @PostMapping("/password")
    public String updateEmail(
            @Valid @ModelAttribute("passwordDto") PasswordDto passwordDto,
            Model model
    ){
        try{
            profileService.updatePassword(passwordDto,customSecurityExpression.getUserId());
            return "redirect:/auth/logout";
        }catch (EntityAlreadyExists | InvalidCredentialsException e){
            UserDto userDto = profileService.findById(customSecurityExpression.getUserId());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", userDto);
            return "/profile";
        }
    }

}
