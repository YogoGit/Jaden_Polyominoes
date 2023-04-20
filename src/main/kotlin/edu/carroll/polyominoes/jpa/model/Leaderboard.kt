package edu.carroll.polyominoes.jpa.model


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Duration
import java.time.LocalDateTime

@Entity
@Table(name = "leaderboard")
class Leaderboard() {

    companion object {
        private val serialVersionUID: Long = 1L
        private val EOL: String = System.lineSeparator()
        private val TAB: String = "\t"
    }

    constructor(
            score: Long,
            level: Long,
            pieces: Long,
            rows: Long,
            time: Duration,
            date: LocalDateTime,
            account: Account
    ) : this() {

        this.time = time
        this.level = level
        this.score = score
        this.rows = rows
        this.pieces = pieces
        this.date = date
        this.account = account
    }

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(name = "time", columnDefinition = "numeric", nullable = false)
    var time: Duration = Duration.ZERO

    @Column(name = "level", nullable = false)
    var level: Long = 0

    @Column(name = "score", nullable = false)
    var score: Long = 0

    @Column(name = "rows", nullable = false)
    var rows: Long = 0

    @Column(name = "pieces", nullable = false)
    var pieces: Long = 0

    @Column(name = "date", nullable = false)
    var date: LocalDateTime? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    lateinit var account: Account

}