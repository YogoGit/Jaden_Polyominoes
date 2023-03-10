package edu.carroll.polyominoes.service.leaderboard

import edu.carroll.polyominoes.jpa.model.Leaderboard
import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.model.datatables.DatatablesResponse
import edu.carroll.polyominoes.web.rest.model.datatables.leaderboard.LeaderboardRow

interface LeaderboardService {
    fun getLeaderboard(request: DatatablesRequest): DatatablesResponse<LeaderboardRow>
}