package edu.carroll.polyominoes.web.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping

class IndexController {

    @GetMapping("/")
    fun index(authentication: Authentication): String {
        return "index";
    }

}