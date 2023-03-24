package edu.carroll.polyominoes.service.account

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.repo.account.LoginRepository
import edu.carroll.polyominoes.service.account.model.SecurityAccount
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCrypt


@SpringBootTest
class UserDetailServiceImplTest(
      @Autowired  private val userDetailsService: UserDetailsService,
      @Autowired  private val loginRepo: LoginRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(UserDetailServiceImplTest::class.java)
        private val username = "testing.user"
        private val email = "test@carroll.edu"
        private val hashPassword = BCrypt.hashpw("testing123!", BCrypt.gensalt())
        private val securityAccount = SecurityAccount(Account(username, email, hashPassword))
    }


    @BeforeEach
    fun beforeTest() {
//        assertNotNull(loginRepo, "loginRepository must be injected", )
//        assertNotNull(userDetailsService, "userDetailsService must be injected")

        // Ensure dummy record is in the DB
        val users = loginRepo.findByUsernameIgnoreCase(username)
        if (users.isEmpty()) {
            loginRepo.save(Account(username, email, hashPassword))
        }
    }

    @Test
    fun loadUserByUsernameSuccessUsernameTest() {
        val user = userDetailsService.loadUserByUsername(username)
        assertEquals(securityAccount, user)

    }

    @Test
    fun loadUserByUsernameSuccessEmailTest() {
        val user = userDetailsService.loadUserByUsername(email)
        assertEquals(securityAccount, user)
    }

    @Test
    fun loadUserByUsernameInvalidUsernameTest() {
        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername(username + "not")
        }

        val expectedMsg = "Found no users for '${username+"not"}'"

        assertEquals(expectedMsg, exception.message)
    }

    @Test
    fun loadUserByUsernameInvalidEmailTest() {
        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername(email + "not")
        }

        val expectedMsg = "Found no users for '${email + "not"}'"

        assertEquals(expectedMsg, exception.message)
    }

    @Test
    fun loadUserByUsernameEmptyTest() {

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername("")
        }

        val expectedMsg = "username was null or blank"

        assertEquals(expectedMsg, exception.message)
    }

    @Test
    fun loadUserByUsernameBlankTest() {

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername(" ")
        }

        val expectedMsg = "username was null or blank"

        assertEquals(expectedMsg, exception.message)
    }

    @Test
    fun loadUserByUsernameNullTest() {

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername(null)
        }

        val expectedMsg = "username was null or blank"

        assertEquals(expectedMsg, exception.message)
    }

    @Test
    fun loadUserByUsernameTabTest() {

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername("\t")
        }

        val expectedMsg = "username was null or blank"

        assertEquals(expectedMsg, exception.message)
    }

    @Test
    fun loadUserByUsernameNewLineTest() {

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername("\n")
        }

        val expectedMsg = "username was null or blank"

        assertEquals(expectedMsg, exception.message)
    }


}

