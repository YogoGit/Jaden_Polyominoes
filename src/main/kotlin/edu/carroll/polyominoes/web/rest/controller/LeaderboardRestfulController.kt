package edu.carroll.polyominoes.web.rest.controller

import edu.carroll.polyominoes.service.leaderboard.LeaderboardService
import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.model.datatables.DatatablesResponse
import edu.carroll.polyominoes.web.rest.model.datatables.leaderboard.LeaderboardRow
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/leaderboard")
class LeaderboardRestfulController(private val leaderboardService: LeaderboardService) {

    @PostMapping
    fun getLeaderboardsPost(@RequestBody request: DatatablesRequest): DatatablesResponse<LeaderboardRow> {
        println("Test")
        return leaderboardService.getLeaderboard(request)
    }
}