package edu.carroll.polyominoes.web.controller

import org.springframework.web.bind.annotation.GetMapping

class IndexController {

    @GetMapping("/")
    fun index(): String {
        return "index";
    }

}