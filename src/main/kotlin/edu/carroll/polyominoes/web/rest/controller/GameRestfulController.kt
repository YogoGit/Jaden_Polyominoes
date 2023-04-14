package edu.carroll.polyominoes.web.rest.controller

import edu.carroll.polyominoes.web.controller.IndexController
import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.ajax.GameStats
import edu.carroll.polyominoes.web.rest.ajax.datatables.DatatablesResponse
import edu.carroll.polyominoes.web.rest.ajax.datatables.leaderboard.LeaderboardRow
import org.slf4j.LoggerFactory
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@EnableWebSecurity
@RestController
@RequestMapping("/api/game")
class GameRestfulController() {

    companion object {
        private val log = LoggerFactory.getLogger(GameRestfulController::class.java)
    }

    @PostMapping
    fun getStatsPost(@RequestBody request: GameStats, authentication: Authentication): Boolean {

        if (!authentication.isAuthenticated) {
            log.warn("getStatsPost: Unauthorized User tried to save a score")
            return false
        }


        return true
    }
}