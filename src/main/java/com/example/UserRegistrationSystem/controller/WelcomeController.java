package com.example.UserRegistrationSystem.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class WelcomeController {

    @GetMapping("")
    public String hello(Model model){
        model.addAttribute("auth", false);
        return "login";
    }

}
