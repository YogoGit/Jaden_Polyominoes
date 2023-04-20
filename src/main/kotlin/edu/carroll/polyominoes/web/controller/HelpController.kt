package edu.carroll.polyominoes.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HelpController {

    @GetMapping("/help")
    fun helpGet(): String {
        return "help"
    }
}