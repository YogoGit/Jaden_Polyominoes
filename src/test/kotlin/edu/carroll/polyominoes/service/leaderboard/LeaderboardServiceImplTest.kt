package edu.carroll.polyominoes.service.leaderboard

import edu.carroll.polyominoes.web.rest.ajax.DatatablesRequest
import edu.carroll.polyominoes.web.rest.ajax.datatables.Column
import edu.carroll.polyominoes.web.rest.ajax.datatables.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class LeaderboardServiceImplTest {

    companion object {

        private val emptyRequest = DatatablesRequest()

        private val mockRequestASC = DatatablesRequest(
                0, 1, 10, listOf(Order(2,"asc")), listOf(Column())
        )
        private val mockRequestDESC = DatatablesRequest(
                0, 1, 10, listOf(Order(2,"desc")), listOf(Column())
        )
    }


    @Test
    fun getLeaderboardSuccessTest() {

    }

    @Test
    fun getLeaderboardRandomDrawSuccessTest() {

    }

    @Test
    fun getLeaderboardRandomStartSuccessTest() {

    }

    @Test
    fun getLeaderboardRandomLengthSuccessTest() {

    }

    @Test
    fun getLeaderboardRandomOrderSuccessTest() {

    }


    @Test
    fun getLeaderboardInvalidDrawSuccessTest() {

    }

    @Test
    fun getLeaderboardInvalidStartSuccessTest() {

    }

    @Test
    fun getLeaderboardInvalidLengthSuccessTest() {

    }

    @Test
    fun getLeaderboardInvalidOrderSuccessTest() {

    }

    @Test
    fun getLeaderboardEmptyRequestSuccessTest() {

    }
}