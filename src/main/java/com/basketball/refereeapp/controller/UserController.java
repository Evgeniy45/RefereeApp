package com.basketball.refereeapp.controller;

import com.basketball.refereeapp.model.User;
import com.basketball.refereeapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Керування користувачами")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Отримати всіх користувачів")
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @Operation(summary = "Отримати інформацію про поточного користувача (OAuth2)")
    @GetMapping("/me")
    public Map<String, Object> currentUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }
}
