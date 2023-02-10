package edu.carroll.polyominoes.web.controller.login

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegisterController {

    @GetMapping("/register")
    fun loginGet(): String {
        return "register";
    }

    @PostMapping("/register")
    fun loginPost(): String {
        return "register";
    }

}