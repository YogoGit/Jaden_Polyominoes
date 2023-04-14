package edu.carroll.polyominoes.web.controller

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@EnableWebSecurity
@Controller
class IndexController() {

    @GetMapping("/")
    fun indexGet(): String {
        return "index"
    }
}
