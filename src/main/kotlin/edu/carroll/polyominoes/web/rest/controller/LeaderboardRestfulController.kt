package edu.carroll.polyominoes.web.rest.controller

import edu.carroll.polyominoes.service.leaderboard.LeaderboardService
import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.ajax.datatables.DatatablesResponse
import edu.carroll.polyominoes.web.rest.ajax.datatables.leaderboard.LeaderboardRow
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@EnableWebSecurity
@RestController
@RequestMapping("/api/leaderboard")
class LeaderboardRestfulController(private val leaderboardService: LeaderboardService) {

    @PostMapping
    fun getLeaderboardsPost(@RequestBody request: DatatablesRequest): DatatablesResponse<LeaderboardRow> {
        return leaderboardService.getLeaderboard(request)
    }
}