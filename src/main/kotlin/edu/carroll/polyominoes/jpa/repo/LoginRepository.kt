package edu.carroll.polyominoes.jpa.repo

import kotlin.collections.List

import edu.carroll.polyominoes.jpa.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

interface LoginRepository : JpaRepository<Login, Int> {

    fun findByUsernameIgnoreCase(username: String) : List<Login>
    fun findByEmailIgnoreCase(email: String) : List<Login>
}