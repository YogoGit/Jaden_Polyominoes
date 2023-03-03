package edu.carroll.polyominoes.web.controller.login

import edu.carroll.polyominoes.service.login.LoginService
import edu.carroll.polyominoes.web.form.LoginForm
import jakarta.validation.Valid
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
@EnableWebSecurity
class LoginController() {

    @GetMapping("/login")
    fun loginGet(model: Model): String {
        model.addAttribute("loginForm", LoginForm())
        return "login"
    }

}