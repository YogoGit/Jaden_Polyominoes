package edu.carroll.polyominoes.web.controller.login

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
@EnableWebSecurity
class LoginController() {

    /**
     * Returns the login page when an HTML get request for /login is recieved
     *
     * @return "login"
     */
    @GetMapping("/login")
    fun loginGet(): String {
        return "login"
    }

}