package edu.carroll.polyominoes.jpa.repo

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.model.Leaderboard
import edu.carroll.polyominoes.jpa.projection.ScoreOnly
import org.springframework.data.jpa.repository.JpaRepository

interface LeaderboardRepo : JpaRepository<Leaderboard, Long> {
    fun findScoreByAccount(account: Account): List<ScoreOnly>
    fun findByAccount(account: Account): List<Leaderboard>
}