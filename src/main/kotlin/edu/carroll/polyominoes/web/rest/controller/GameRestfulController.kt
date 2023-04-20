package edu.carroll.polyominoes.web.rest.controller

import edu.carroll.polyominoes.service.game.GameService
import edu.carroll.polyominoes.web.rest.ajax.GameStats
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@EnableWebSecurity
@RestController
@RequestMapping("/api/game")
class GameRestfulController(private val gameService: GameService) {

    companion object {
        private val log = LoggerFactory.getLogger(GameRestfulController::class.java)
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun getStatsPost(@RequestBody stats: GameStats): Boolean {

        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null) {
            log.debug("getStatsPost: Authentication was null")
            return false;
        }

        log.debug("getStatsPost: is User Authenticated {}", authentication.isAuthenticated)
        if (!authentication.isAuthenticated) {
            log.debug("getStatsPost: Unauthorized User tried to save a score")
            return false
        }

        val isHighScore = gameService.checkHighScore(authentication.name, stats.score)

        if (isHighScore) {
            gameService.saveGameStats(authentication.name, stats)
            return true
        }

        return false
    }
}