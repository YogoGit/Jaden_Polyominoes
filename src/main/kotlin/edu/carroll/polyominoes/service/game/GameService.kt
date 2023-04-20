package edu.carroll.polyominoes.service.game

import edu.carroll.polyominoes.web.rest.ajax.GameStats

interface GameService {

    /**
     *  When given a username and GameStats saves them to the corresponding in the database for the given user.
     *
     * @param username: A String representing the user to link the game statistics to.
     * @param stats: A GameStats containing game information such as.
     *
     * @return Returns true if the game statistics is saved; false otherwise.
     */
    fun saveGameStats(username: String, stats: GameStats): Boolean

    /**
     *  When given a username and score checks if the score is the user's high score.
     *
     * @param username: A String representing the user to link the game statistics to.
     * @param score: A Long representing the score a user got in the game.
     *
     * @return Returns true the score is a new high score for the user; false otherwise.
     */
    fun checkHighScore(username: String, score: Long): Boolean
}