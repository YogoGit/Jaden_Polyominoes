package edu.carroll.polyominoes.web.controller.login

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class LoginController {

    @GetMapping("/login")
    fun loginGet(): String {
        return "login";
    }

    @PostMapping("/login")
    fun loginPost(): String {
        return "login";
    }

}