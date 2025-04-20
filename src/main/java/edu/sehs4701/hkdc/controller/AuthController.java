package edu.sehs4701.hkdc.controller;

import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.request.SigninRequest;
import edu.sehs4701.hkdc.core.payload.request.SignupRequest;
import edu.sehs4701.hkdc.core.payload.response.AuthResponseDto;
import edu.sehs4701.hkdc.core.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final AuthService authService;

    @GetMapping({"/", "/home"})
    public String homePage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", currentUser);
        return "home";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute SignupRequest signupRequest,
            RedirectAttributes flash,
            Model model
    ) {
        try {
            AuthResponseDto dto = authService.signup(signupRequest);
            flash.addFlashAttribute("message", "Registration successful – please sign in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) model.addAttribute("loginError", "Invalid credentials");
        if (logout != null) model.addAttribute("loginMsg", "You have signed out");
        model.addAttribute("signinRequest", new SigninRequest());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @ModelAttribute SigninRequest signinRequest,
            Model model
    ) {
        try {
            authService.signin(signinRequest);
            return "redirect:/";
        } catch (AuthenticationException | IllegalArgumentException e) {
            model.addAttribute("loginError", "Invalid email or password");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}
