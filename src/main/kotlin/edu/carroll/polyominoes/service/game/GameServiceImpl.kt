package edu.carroll.polyominoes.service.game

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.model.Leaderboard
import edu.carroll.polyominoes.jpa.repo.AccountRepository
import edu.carroll.polyominoes.jpa.repo.LeaderboardRepo
import edu.carroll.polyominoes.web.rest.ajax.GameStats
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class GameServiceImpl(private val leaderboardRepo: LeaderboardRepo, private val accountRepo: AccountRepository) :
        GameService {
    companion object {
        private val log = LoggerFactory.getLogger(GameServiceImpl::class.java)
    }

    /**
     *  When given a username and GameStats saves them to the corresponding in the database for the given user.
     *
     * @param user: A String representing the user to link the game statistics to.
     * @param stats: A GameStats containing game information such as.
     *
     * @return Returns true if the game statistics is saved; false otherwise.
     */
    override fun saveGameStats(username: String, stats: GameStats): Boolean {

        val user = findUser(username);
        if (user == null) {
            log.debug("saveGameStats: found no users for '{}'", username)
            return false
        }

        val leaderboardRecord = leaderboardRepo.findByAccount(user);

        if (leaderboardRecord.size > 1) {
            log.warn("saveGameStats: found {} leaderboard records for '{}'", leaderboardRecord.size, username)
            return false
        }

        val leaderboard = Leaderboard(
                stats.score, stats.level, stats.pieces, stats.rows, stats.time, LocalDateTime.now(
                Clock.system(ZoneId.of("America/Denver"))
        ), user
        )

        if (leaderboardRecord.isEmpty()) {
            leaderboardRepo.save(leaderboard)
            log.info("saveGameStats: Created game stats for {}", username)
            return true
        }

        val existingLeaderboard = leaderboardRecord[0]
        existingLeaderboard.score = stats.score
        existingLeaderboard.level = stats.level
        existingLeaderboard.pieces = stats.pieces
        existingLeaderboard.rows = stats.rows
        existingLeaderboard.time = stats.time
        existingLeaderboard.date = LocalDateTime.now(Clock.system(ZoneId.of("America/Denver")))

        leaderboardRepo.save(existingLeaderboard)

        log.info("saveGameStats: Updated game stats for {}", username)

        return true;
    }

    /**
     *  When given a username and score checks if the score is the user's high score.
     *
     * @param username: A String representing the user to link the game statistics to.
     * @param score: A Long representing the score a user got in the game.
     *
     * @return Returns true the score is a new high score for the user; false otherwise.
     */
    override fun checkHighScore(username: String, score: Long): Boolean {
        val user = findUser(username);
        if (user == null) {
            log.debug("checkHighScore: found no users for '{}'", username)
            return false
        }

        val scores = leaderboardRepo.findScoreByAccount(user);

        if (scores.size > 1) {
            log.warn("checkHighScore: found {} scores for '{}'", scores.size, username)
            return false
        }

        if (scores.isEmpty()) {
            log.info("checkHighScore: user '{}' had no prior score", username)
            return true
        }

        val currentScore = scores[0].getScore()

        return if (score > currentScore) {
            log.info("checkHighScore: the score '{}' is a new high score for user '{}'", score, username)
            true
        } else if (score == currentScore) {
            log.info("checkHighScore: the score '{}' is equal to the high score for user '{}'", score, username)
            false
        } else {
            log.info("checkHighScore: the score '{}' is not a high score for user '{}'", score, username)
            false
        }

    }

    /**
     * When given a username returns the corresponding Account
     *
     * @param  username: A String representing a user in the database.
     *
     * @return Returns an Account corresponding to the username; otherwise null;
     */
    private fun findUser(username: String): Account? {

        log.debug("findUser: attempting to find user '{}'", username)

        val users = accountRepo.findByUsernameIgnoreCase(username)

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size != 1) {
            if (users.size > 1) {
                log.warn("findUser: found {} users for '{}'", users.size, username)
            } else {
                log.debug("findUser: found no users for '{}'", username)
            }
            return null
        }

        log.info("findUser: Found user linked to '{}'", username)
        return users[0];
    }

}