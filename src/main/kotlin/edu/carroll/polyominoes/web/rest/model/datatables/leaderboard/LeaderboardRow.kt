package edu.carroll.polyominoes.web.rest.model.datatables.leaderboard

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import edu.carroll.polyominoes.jpa.model.Leaderboard
import java.time.LocalDateTime

class LeaderboardRow() {

    constructor(index: Long, leaderboard: Leaderboard) : this() {
        this.index = index
        username = leaderboard.account.username
        score = leaderboard.score
        polyominoes = leaderboard.polyominoes
        time = leaderboard.time.toMillis() / 60000f
        date = leaderboard.date
    }

    var index: Long? = null
    var username: String? = null
    var score: Long? = null
    var polyominoes: Long? = null
    var time: Float? = null

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    var date: LocalDateTime? = null
}