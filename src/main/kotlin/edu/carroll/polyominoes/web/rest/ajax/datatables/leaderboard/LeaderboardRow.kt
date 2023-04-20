package edu.carroll.polyominoes.web.rest.ajax.datatables.leaderboard

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import edu.carroll.polyominoes.jpa.model.Leaderboard
import java.time.LocalDateTime

class LeaderboardRow() {

    constructor(index: Long, leaderboard: Leaderboard) : this() {
        this.index = index
        this.username = leaderboard.account.username
        this.score = leaderboard.score
        this.level = leaderboard.level
        this.rows = leaderboard.rows
        this.pieces = leaderboard.pieces
        this.time = leaderboard.time.toMillis() / 1000f
        this.date = leaderboard.date
    }

    var index: Long? = null
    var username: String? = null
    var score: Long? = null
    var level: Long? = null
    var rows: Long? = null
    var pieces: Long? = null
    var time: Float? = null

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    var date: LocalDateTime? = null
}