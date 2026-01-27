package com.example.karya_lab10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to handle login and navigation requests.
 */
@Controller
public class LoginController {

    /**
     * Maps HTTP GET /login to the login.html view.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Maps HTTP GET /home to a success page after login.
     */
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}