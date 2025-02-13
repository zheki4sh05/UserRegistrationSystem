package com.example.UserRegistrationSystem.controller;
import com.example.UserRegistrationSystem.dto.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class WelcomeController {

    @GetMapping("")
    public String hello(
            Model model
    ){
        AuthDto authDto= new AuthDto();
        model.addAttribute("auth", false);
        model.addAttribute("authDto", authDto);
        model.addAttribute("error", "");
        return "login";
    }

}
