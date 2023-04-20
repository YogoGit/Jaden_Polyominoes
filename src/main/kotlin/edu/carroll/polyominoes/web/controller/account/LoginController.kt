package edu.carroll.polyominoes.web.controller.account

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
@EnableWebSecurity
class LoginController {

    /**
     * Returns the login page when an HTML get request for /login is received.
     *
     * @return "login"
     */
    @GetMapping("/login")
    fun loginGet(): String {
        return "login"
    }

}