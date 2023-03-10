package edu.carroll.polyominoes.jpa.repo

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.model.Leaderboard
import org.springframework.data.jpa.repository.JpaRepository

import org.springframework.data.repository.PagingAndSortingRepository
import java.awt.print.Pageable

/**
 * https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-datatable
 * @see <a href="https://www.baeldung.com/spring-data-jpa-pagination-sorting">Baeldung Pagination and Sorting using Spring Data JPA</a>
 */
interface LeaderboardRepo : JpaRepository<Leaderboard, Long> {
    fun findByAccount(account: Account): List<Leaderboard>
}