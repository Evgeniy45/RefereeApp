package com.basketball.refereeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "OAuth2 Controller", description = "Отримання інформації про поточного користувача після OAuth2-автентифікації")
public class OAuth2Controller {

    @Operation(summary = "Повертає атрибути поточного автентифікованого користувача")
    @GetMapping("/me")
    public Map<String, Object> currentUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }
}
