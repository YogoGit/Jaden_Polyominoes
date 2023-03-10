package edu.carroll.polyominoes.web.controller

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.model.Leaderboard
import edu.carroll.polyominoes.jpa.repo.LeaderboardRepo
import edu.carroll.polyominoes.jpa.repo.account.LoginRepository
import edu.carroll.polyominoes.web.form.ScoreForm
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.time.Duration
import java.time.LocalDateTime


@Controller
class IndexController(private val leaderboardRepo: LeaderboardRepo, private val loginRepo: LoginRepository) {

    companion object {
        private val log = LoggerFactory.getLogger(IndexController::class.java)
    }

    @GetMapping("/")
    fun indexGet(model: Model): String {
        model.addAttribute("scoreForm", ScoreForm())
        return "index"
    }

    @PostMapping("/")
    fun saveScorePost(
            @Valid @ModelAttribute scoreForm: ScoreForm,
            result: BindingResult,
            authentication: Authentication
    ): String? {

        if (result.hasErrors()) {
            return null
        }

        if (!authentication.isAuthenticated) {
            log.warn("saveScorePost: Unauthorized User tried to save a score")
            return "redirect:/login"
        }


        val username = (authentication.principal as UserDetails).username
        val users = loginRepo.findByUsernameIgnoreCase(username)

        if (users.size != 1) {
            if (users.size > 1) {
                log.warn("saveScorePost: found {} users for '{}'", users.size, username)
            } else {
                log.debug("saveScorePost: found no users for {}", username)
            }
            result.addError(ObjectError("globalError", "Could not save score"))
            return null
        }

        val user = users[0]

        saveScore(user, scoreForm)


        return "redirect:/leaderboard"
    }


    private fun saveScore(account: Account, scoreForm: ScoreForm) {
        val leaderboard =
            Leaderboard(scoreForm.score, scoreForm.polyominoes, Duration.ofMillis(scoreForm.milliseconds), LocalDateTime.now(), account)
        leaderboardRepo.save(leaderboard)
    }
}
