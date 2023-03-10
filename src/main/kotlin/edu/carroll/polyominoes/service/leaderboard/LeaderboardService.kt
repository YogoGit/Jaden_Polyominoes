package edu.carroll.polyominoes.service.leaderboard

import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.model.datatables.DatatablesResponse
import edu.carroll.polyominoes.web.rest.model.datatables.leaderboard.LeaderboardRow

/**
 * When given a request creates DatatablesResponse of Leaderboard rows that represents the information in the
 * leaderboard table matching the page and sorting.
 *
 * @param request: A DatatableRequest containing starting index, size, and sorting parameters
 * @return Returns response containing the rows from the leaderboard table matching the given request otherwise an empty response
 *
 */
interface LeaderboardService {
    fun getLeaderboard(request: DatatablesRequest): DatatablesResponse<LeaderboardRow>
}