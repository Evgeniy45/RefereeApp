package com.basketball.refereeapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal Object principal) {
        System.out.println("⛳ principal: " + principal);
        String username = "Гість";
        String role = "UNKNOWN";

        if (principal instanceof UserDetails user) {
            username = user.getUsername();
            role = user.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .orElse("USER");
        } else if (principal instanceof OAuth2User oauthUser) {
            username = oauthUser.getAttribute("name");
            role = "OAUTH2_USER";
        }

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        return "home";
    }
}
