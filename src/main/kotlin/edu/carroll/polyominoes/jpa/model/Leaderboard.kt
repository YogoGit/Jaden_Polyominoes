package edu.carroll.polyominoes.jpa.model


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.time.Duration

@Entity
@Table(name = "leaderboard")
class Leaderboard() {

    companion object {
        private val serialVersionUID: Long = 1L
        private val EOL: String = System.lineSeparator()
        private val TAB: String = "\t"
    }

    constructor(score: Long, polyominoes: Long, time: Duration, date: LocalDateTime, account: Account) : this() {
        this.score = score
        this.polyominoes = polyominoes
        this.time = time
        this.date = date
        this.account = account
    }

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(name = "score", nullable = false)
    var score: Long? = null

    @Column(name = "polyominoes", nullable = false)
    var polyominoes: Long? = null

    @Column(name = "time", columnDefinition = "numeric", nullable = false)
    var time: Duration = Duration.ZERO

    @Column(name = "date", nullable = false)
    var date: LocalDateTime? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    lateinit var account: Account

}