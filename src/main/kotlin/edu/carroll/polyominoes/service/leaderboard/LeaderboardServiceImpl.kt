package edu.carroll.polyominoes.service.leaderboard

import edu.carroll.polyominoes.jpa.repo.LeaderboardRepo
import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.model.datatables.DatatablesResponse
import edu.carroll.polyominoes.web.rest.model.datatables.leaderboard.LeaderboardRow
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class LeaderboardServiceImpl(private val leaderboardRepo: LeaderboardRepo) : LeaderboardService {

    companion object {
        private val log = LoggerFactory.getLogger(LeaderboardServiceImpl::class.java)
        private val emptyLeaderboard by lazy {
            DatatablesResponse<LeaderboardRow>(
                    mutableListOf<LeaderboardRow>(), 0, 0, 0
            )
        }
    }


    /**
     *  Represents the valid column in the leaderboard object
     */
    private enum class Column {
        position, username, score, polyominoes, time, date
    }

    /**
     * When given a request creates DatatablesResponse of Leaderboard rows that represents the information in the
     * leaderboard table matching the page and sorting.
     *
     * @param request: A DatatableRequest containing starting index, size, and sorting parameters
     * @return Returns response containing the rows from the leaderboard table matching the given request otherwise an empty response
     *
     */
    override fun getLeaderboard(request: DatatablesRequest): DatatablesResponse<LeaderboardRow> {

        if (request.length < 0 && request.start < 0) {
            log.warn("getLeaderboard: start is '{}' and length is '{}'", request.start, request.length)
            return emptyLeaderboard
        }

        val pageSize = request.length
        val pageNum = request.start / pageSize
        lateinit var pageable: Pageable

        log.info("getLeaderboard: Getting leaderboard page '{}' with a size of '{}'", pageNum, pageSize)

        if (request.order.size != 1) {
            if (request.order.size > 1) {
                log.warn("getLeaderboard: leaderboard order was {}", request.order.size)
            } else {
                log.warn("getLeaderboard: leaderboard order was empty")
            }
            return emptyLeaderboard
        }

        val order = request.order[0]

        if (order.column != null) {
            if (!(Column.values().size >= order.column!! || order.column!! > 0)) {
                log.warn("getLeaderboard: Order column index is out of bounds order.column '{}' and Column.values '{}'", order.column, Column.values().size)
                pageable = PageRequest.of(pageNum, pageSize)
            } else {
                lateinit var sort : Sort
                when (order.dir) {
                    "asc" -> {
                        println(order.dir)
                        sort = Sort.by(Sort.Direction.ASC ,Column.values()[order.column!!].toString())
                    }
                    "desc" -> {
                        sort = Sort.by(Sort.Direction.DESC ,Column.values()[order.column!!].toString())
                    }
                    else -> {
                        log.warn(
                                "getLeaderboard: '{}' is an invalid entry for Order direction defaulting to desc",
                                order.dir
                        )
                        sort = Sort.by(Sort.Direction.DESC ,Column.values()[order.column!!].toString())
                    }
                }
                pageable = PageRequest.of(pageNum, pageSize, sort)
            }
        }

        val leaderboards = leaderboardRepo.findAll(pageable)

        val leaderboardRows = mutableListOf<LeaderboardRow>()
        leaderboards.forEachIndexed { index, row ->
            leaderboardRows.add(LeaderboardRow((index + (pageNum * pageSize) + 1).toLong(), row))
        }
        val entryCount = leaderboardRepo.count()

        return DatatablesResponse<LeaderboardRow>(leaderboardRows, entryCount, entryCount, request.draw)
    }
}
