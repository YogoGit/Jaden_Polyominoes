package edu.carroll.polyominoes.web.controller.login

import edu.carroll.polyominoes.service.RegisterService
import edu.carroll.polyominoes.web.form.LoginForm
import edu.carroll.polyominoes.web.form.RegisterForm
import jakarta.validation.Valid
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegisterController(private val registerService: RegisterService) {

    @GetMapping("/register")
    fun loginGet(model: Model): String {
        model.addAttribute("registerForm", RegisterForm())
        return "register";
    }

    @PostMapping("/register")
    fun loginPost(@Valid @ModelAttribute registerForm: RegisterForm, result : BindingResult): String? {
        var errorOccured = false

        if (result.hasErrors()) {
            return null;
        }
        if (!registerService.validateUniqueUsername(registerForm)) {
            result.addError(ObjectError("username", "Username is taken"))
            errorOccured = true;
        }
        if (!registerService.validateUniqueEmail(registerForm)) {
            result.addError(ObjectError("email", "Email is taken"))
            errorOccured = true;
        }
        if (!registerService.validateConfirmPassword(registerForm)) {
            result.addError(ObjectError("password", "Passwords do not match"))
            errorOccured = true;
        }
        if (errorOccured) {
            return null;
        }

        if(!registerService.createUser(registerForm)) {
            result.addError(ObjectError("globalError", "Account was unable to be created"))
            return null;
        }

        return "redirect:/";
    }

}