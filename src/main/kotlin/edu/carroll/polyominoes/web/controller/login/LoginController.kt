package edu.carroll.polyominoes.web.controller.login

import edu.carroll.polyominoes.service.login.LoginService
import edu.carroll.polyominoes.web.form.LoginForm
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class LoginController(private val loginService: LoginService) {

    @GetMapping("/login")
    fun loginGet(model: Model): String {
        model.addAttribute("loginForm", LoginForm())
        return "login"
    }

    @PostMapping("/login")
    fun loginPost(@Valid @ModelAttribute loginForm : LoginForm, result : BindingResult): String? {

        if (result.hasErrors()) {
            return null
        }
        if (!loginService.validateUser(loginForm)) {
            result.addError(ObjectError("globalError", "Username, email, or password is incorrect"))
            return null
        }
        return "redirect:/"
    }


}