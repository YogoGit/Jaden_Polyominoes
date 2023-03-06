package edu.carroll.polyominoes.web.controller.login

import edu.carroll.polyominoes.service.login.RegisterService
import edu.carroll.polyominoes.web.form.RegisterForm
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
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
class RegisterController(private val registerService: RegisterService) {

    companion object {
        private val log = LoggerFactory.getLogger(RegisterController::class.java)
    }

    /**
     * Given a model returns the register page with a RegisterForm when an HTML get request is received.
     *
     * @param model : Model
     * @return "register"
     */
    @GetMapping("/register")
    fun loginGet(model: Model): String {
        model.addAttribute("registerForm", RegisterForm())
        return "register";
    }

    /**
     * Given a registerForm and a BindingResult will redirect to log in or show errors on the register page when
     * an HTML Post request is received.
     *
     * @param registerForm : Form containing a new User's information such as Username, Email, and Raw Passwords.
     * @param result : BindingResult
     * @return "redirect:/login", otherwise null.
     */

    @PostMapping("/register")
    fun loginPost(@Valid @ModelAttribute registerForm: RegisterForm, result: BindingResult): String? {
        var errorOccured = false

        if (result.hasErrors()) {
            return null;
        }
        if (!registerService.validateUniqueUsername(registerForm.username)) {
            result.rejectValue("username", "error.user", "Username is taken")
            errorOccured = true;
        }
        if (!registerService.validateUniqueEmail(registerForm.email)) {
            result.rejectValue("email", "error.email", "Email is taken")
            errorOccured = true;
        }
        if (!validateConfirmPassword(registerForm.rawPassword, registerForm.rawPasswordConfirm)) {
            result.rejectValue("passwordConfirm", "error.passwordConfirm", "Passwords do not match")
            errorOccured = true;
        }
        if (errorOccured) {
            return null;
        }

        if (!registerService.createUser(registerForm.username, registerForm.email, registerForm.rawPassword)) {
            result.addError(ObjectError("globalError", "Account was unable to be created"))
            return null;
        }

        return "redirect:/login";
    }

    /**
     * Given a registerForm, determine if the password provided match.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the passwords match, false otherwise
     */
    private fun validateConfirmPassword(rawPassword: String, rawPasswordConfirm: String): Boolean {
        if (rawPassword != rawPasswordConfirm) {
            log.debug("validateConfirmPassword: passwords !match");
            return false
        }

        log.debug("validateConfirmPassword: passwords match");
        return true
    }

}