package edu.carroll.polyominoes.jpa.repo

import edu.carroll.polyominoes.jpa.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByUsernameIgnoreCase(username: String?): List<Account>
    fun findByEmailIgnoreCase(email: String?): List<Account>
}