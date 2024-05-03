package com.example.narxoz.controllers;

import com.example.narxoz.services.UserDetailService;
import com.example.narxoz.services.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.narxoz.models.User;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/2")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String mains(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "main";
    }

    @GetMapping("/registration")
    public String registration(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "register";
    }


    @PostMapping("/registration")
    public String createUser(User user, Model model) throws MessagingException, UnsupportedEncodingException {
        if(!userService.createUser(user)){
            model.addAttribute("errorMessage", "Пользователь с email: "+user.getSkFk()+" уже сущенствует");
            return "register";
        }
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String login(Principal principal,Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "profile";
    }
}
