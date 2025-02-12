package com.example.UserRegistrationSystem.controller;

import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final IProfileService profileService;


    @GetMapping("")
   public String getUserProfile(
            @RequestParam("id") String userId,
            Model model
    ){
        UserDto userDto = profileService.findById(userId);
        model.addAttribute("user", userDto);
        model.addAttribute("auth", true);
        return "profile";
    }


    @PostMapping("")
    public String updateUserProfile(
            @ModelAttribute("userDto") UserDto userDto,
            Model model
    ){
        UserDto updatedUserDto = profileService.update(userDto);
        model.addAttribute("user", updatedUserDto);
        model.addAttribute("auth", true);
        return "profile";
    }

    @DeleteMapping("")
    public String deleteUserProfile(
            @RequestParam("id") String userId
    ){
      profileService.deleteById(userId);
      return "redirect:/signup";
    }





}
