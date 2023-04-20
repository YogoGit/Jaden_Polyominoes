package edu.carroll.polyominoes.service.game

import edu.carroll.polyominoes.service.account.RegisterServiceImpl
import edu.carroll.polyominoes.service.account.RegisterServiceImplTest
import edu.carroll.polyominoes.web.rest.ajax.GameStats
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@SpringBootTest
internal class GameServiceImplTest(
        @Autowired private val gameServiceImpl: GameServiceImpl,
        @Autowired private val registerServiceImpl: RegisterServiceImpl
) {
          companion object {
              private val testAccount1 =
                  RegisterServiceImplTest.TestingAccount("testing.user1", "test1@carroll.edu", "testing456!")
              private val emptyGameStats = GameStats()
              private val testGameStats1 = GameStats(2.2, 1, 200, 2, 5)
              private val testGameStats2 = GameStats(14.2, 5, 1200, 20, 100)
          }


    internal class TestingAccount {
        var username: String
        var email: String
        var rawPassword: String

        constructor(username : String, email : String, rawPassword : String) {
            this.username = username
            this.email = email
            this.rawPassword = rawPassword
        }
    }

    @Test
    fun checkHighScoreNoUserTest() {
        assertFalse(gameServiceImpl.checkHighScore("user", 0))
    }

    @Test
    fun checkHighScoreUserExistAndHighScore() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(gameServiceImpl.checkHighScore(testAccount1.username, 100))
    }

    @Test
    fun checkHighScoreUserExistAndNewHighScore() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(gameServiceImpl.saveGameStats(testAccount1.username, testGameStats1))
        assertTrue(gameServiceImpl.checkHighScore(testAccount1.username, 1500))
    }

    @Test
    fun checkHighScoreUserExistAndNotHighScore() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(gameServiceImpl.saveGameStats(testAccount1.username, testGameStats1))
        assertFalse(gameServiceImpl.checkHighScore(testAccount1.username, 100))
    }

    @Test
    fun checkHighScoreEmptyUsername() {
        assertFalse(gameServiceImpl.checkHighScore("", 0))
    }

    @Test
    fun checkHighScoreBlankUsername() {
        assertFalse(gameServiceImpl.checkHighScore("    ", 0))
    }

    @Test
    fun checkHighScoreTabUsername() {
        assertFalse(gameServiceImpl.checkHighScore("\t", 0))
    }

    @Test
    fun checkHighScoreNewLineUsername() {
        assertFalse(gameServiceImpl.checkHighScore("\n", 0))
    }

    @Test
    fun saveGameStatsUserExists() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(gameServiceImpl.saveGameStats(testAccount1.username, testGameStats1))
    }

    @Test
    fun saveGameStatsUpdateStats() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(gameServiceImpl.saveGameStats(testAccount1.username, testGameStats1))
        assertTrue(gameServiceImpl.saveGameStats(testAccount1.username, testGameStats2))
    }

    @Test
    fun saveGameStatsUserDoesNotExist() {
        assertFalse(gameServiceImpl.saveGameStats(testAccount1.username, testGameStats1))
    }

    @Test
    fun saveGameStatsGameStatsIsEmpty() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertFalse(gameServiceImpl.saveGameStats(testAccount1.username, emptyGameStats))
    }

    @Test
    fun saveGameStatsEmptyUsername() {
        assertFalse(gameServiceImpl.saveGameStats("", testGameStats1))
    }

    @Test
    fun saveGameStatsBlankUsername() {
        assertFalse(gameServiceImpl.saveGameStats("   ", testGameStats1))
    }

    @Test
    fun saveGameStatsTabUsername() {
        assertFalse(gameServiceImpl.saveGameStats("\t", testGameStats1))
    }

    @Test
    fun saveGameStatsNewLineUsername() {
        assertFalse(gameServiceImpl.saveGameStats("\n", testGameStats1))
    }


}