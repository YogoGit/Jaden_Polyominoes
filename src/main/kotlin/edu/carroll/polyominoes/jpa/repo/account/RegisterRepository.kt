package edu.carroll.polyominoes.jpa.repo.account

import edu.carroll.polyominoes.jpa.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface RegisterRepository : JpaRepository<Account, Long> {

    fun findByUsernameIgnoreCase(username: String): List<Account>
    fun findByEmailIgnoreCase(email: String): List<Account>
}