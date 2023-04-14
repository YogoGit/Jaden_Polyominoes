package edu.carroll.polyominoes.web.rest.ajax

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Duration
import java.time.LocalDateTime

class GameStats() {

    constructor(time: Double, level: Long, score: Long, rows: Long, pieces: Long): this() {
        this.time = Duration.ofMillis((time*1000).toLong())
        this.level = level
        this.score = score
        this.rows = rows
        this.pieces = pieces
        this.date = LocalDateTime.now();
    }

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var time: Duration = Duration.ZERO

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var level: Long = 0

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var score: Long = 0

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var rows: Long = 0

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var pieces: Long = 0

    @field:NotNull
    var date: LocalDateTime? = null;

}