package org.attractor.microgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.exception.RegisterException;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/auth/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/registration";
    }

    @PostMapping("/auth/register")
    public String registerUser(@Valid @ModelAttribute UserDto userDto, BindingResult result, Model model) {
        log.info("Registering new user: {}", userDto.getUsername());
        if (result.hasErrors()) {
            log.warn("Validation errors during registration: {}", result.getAllErrors());
            return "auth/registration";
        }
        try {
            userService.registerUser(userDto);
            log.info("User registered successfully: {}", userDto.getUsername());
            return "redirect:/auth/login";
        } catch (RegisterException e) {
            log.warn("Registration failed: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "auth/registration";
        }
    }
}