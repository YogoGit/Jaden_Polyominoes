package edu.carroll.polyominoes.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class LeaderboardController {
    @GetMapping("/leaderboard")
    fun leaderboardGet(): String {
        return "leaderboard"
    }
}