package ru.isshepelev.note.ui.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.isshepelev.note.infrastructure.exception.UsernameAlreadyExistsException;
import ru.isshepelev.note.infrastructure.service.UserService;
import ru.isshepelev.note.ui.dto.SignUpDto;

@Controller
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute SignUpDto signUpDto, Model model) {
        try {
            userService.registerUser(signUpDto);
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("error", "Пользователь уже существует");
            return "register";
        }
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


}
