package com.bookstore.controller;

import com.bookstore.entity.AppUser;
import com.bookstore.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
public class AuthController {
    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    public AuthController(AppUserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }
    @GetMapping("/register")
    public String registerForm(Model model) { model.addAttribute("user", new AppUser()); return "register"; }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") AppUser user, Model model) {
        if (users.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại."); return "register";
        }
        if (user.getEmail() != null && !user.getEmail().isBlank() && users.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã được sử dụng."); return "register";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        users.save(user);
        return "redirect:/login?registered";
    }
}
