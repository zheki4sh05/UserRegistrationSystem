package com.example.UserRegistrationSystem.controller;

import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.service.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

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
        return "profile";
    }


    @PutMapping("")
    public String updateUserProfile(
            @ModelAttribute("user") UserDto userDto,
            Model model
    ){
        UserDto updatedUserDto = profileService.update(userDto);
        model.addAttribute("user", updatedUserDto);
        return "profile";
    }





}
