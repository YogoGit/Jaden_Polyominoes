package edu.carroll.polyominoes.service.account

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.repo.account.RegisterRepository
import edu.carroll.polyominoes.service.account.model.TestingAccount
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.test.context.event.annotation.AfterTestClass
import kotlin.test.AfterTest

@SpringBootTest
class RegisterServiceImplTest(
        @Autowired private val registerServiceImpl: RegisterServiceImpl,
        @Autowired private val registerRepo : RegisterRepository
) {

    companion object {

        private val currentAccount = TestingAccount("testing.user", "test@carroll.edu", "testing123!")
        private val testAccount1 = TestingAccount("testing.user1", "test1@carroll.edu", "testing456!")
        private val testAccount2 = TestingAccount("testing.user2", "test2@gmail.com", "testing7890!")
    }


    @BeforeEach
    fun beforeTest() {
        assertNotNull(registerRepo, "registerRepo must be injected", )
        assertNotNull(registerServiceImpl, "registerServiceImpl, must be injected")

        // Ensure dummy record is in the DB
        val users = registerRepo.findByUsernameIgnoreCase(currentAccount.username)
        if (users.isEmpty()) {
            val hashPassword = BCrypt.hashpw(currentAccount.rawPassword, BCrypt.gensalt())
            registerRepo.save(Account(currentAccount.username, currentAccount.email, hashPassword))
        }
    }

    @AfterEach
    fun afterTest() {

        // removing test accounts from DB
        var users = registerRepo.findByUsernameIgnoreCase(testAccount1.username)
        if (users.isNotEmpty()) {
            registerRepo.deleteAll(users)
        }

        users = registerRepo.findByUsernameIgnoreCase(testAccount2.username)
        if (users.isNotEmpty()) {
            registerRepo.deleteAll(users)
        }
    }

    @AfterTestClass
    fun afterClass() {
        var users = registerRepo.findByUsernameIgnoreCase(currentAccount.username)
        if (users.isNotEmpty()) {
            registerRepo.deleteAll(users)
        }
    }

    @Test
    fun createUserOneUserSuccessTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))

        val users = registerRepo.findByUsernameIgnoreCase(testAccount1.username)
        assertEquals(1, users.size)

        val user = users[0]
        assertEquals(user.username, testAccount1.username)
        assertEquals(user.email, testAccount1.email)
        assertTrue(BCrypt.checkpw(testAccount1.rawPassword, user.hashPassword))
    }

    @Test
    fun createUserTwoUserSuccessTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))

        // testAccount1
        val users1 = registerRepo.findByUsernameIgnoreCase(testAccount1.username)
        assertEquals(1, users1.size)

        val user1 = users1[0]
        assertEquals(user1.username, testAccount1.username)
        assertEquals(user1.email, testAccount1.email)
        assertTrue(BCrypt.checkpw(testAccount1.rawPassword, user1.hashPassword))

        // testAccount2
        val users2 = registerRepo.findByUsernameIgnoreCase(testAccount1.username)
        assertEquals(1, users2.size)

        val user2 = users2[0]
        assertEquals(user2.username, testAccount1.username)
        assertEquals(user2.email, testAccount1.email)
        assertTrue(BCrypt.checkpw(testAccount1.rawPassword, user2.hashPassword))
    }

    @Test
    fun createUserUsernameExistsTest() {
        assertFalse(registerServiceImpl.createUser(currentAccount.username, testAccount1.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserEmailExistsTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, currentAccount.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserUserExistsTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, currentAccount.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserDoubleCreationTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserTwoUserDoubleCreationTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))

        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
    }

    @Test
    fun createUserEmptyUsernameTest() {
        assertFalse(registerServiceImpl.createUser("", testAccount1.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserBlankUsernameTest() {
        assertFalse(registerServiceImpl.createUser("    ", testAccount1.email, testAccount1.rawPassword))
    }
    @Test
    fun createUserTabUsernameTest() {
        assertFalse(registerServiceImpl.createUser("\t", testAccount1.email, testAccount1.rawPassword))
    }
    @Test
    fun createUserNewlineUsernameTest() {
        assertFalse(registerServiceImpl.createUser("\n", testAccount1.email, testAccount1.rawPassword))
    }
    @Test
    fun createUserEmptyEmailTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username,"", testAccount1.rawPassword))
    }

    @Test
    fun createUserBlankEmailTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email,"    "))
    }
    @Test
    fun createUserTabEmailTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, "\t", testAccount1.rawPassword))
    }
    @Test
    fun createUserNewlineEmailTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, "\n", testAccount1.rawPassword))
    }
    @Test
    fun createUserEmptyPasswordTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email,""))
    }

    @Test
    fun createUserBlankPasswordTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email,"    "))
    }
    @Test
    fun createUserTabPasswordTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email,"\t"))
    }
    @Test
    fun createUserNewlinePasswordTest() {
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email,"\n"))
    }

    @Test
    fun validateUniqueUsernameOneSuccessTest() {
        assertTrue(registerServiceImpl.validateUniqueUsername(testAccount1.username))
    }

    @Test
    fun validateUniqueUsernameTwoSuccessTest() {
        assertTrue(registerServiceImpl.validateUniqueUsername(testAccount1.username))
        assertTrue(registerServiceImpl.validateUniqueUsername(testAccount2.username))
    }

    @Test
    fun validateUniqueUsernameInvalidTest() {
        assertFalse(registerServiceImpl.validateUniqueUsername(currentAccount.username))
    }

    @Test
    fun validateUniqueUsernameEmptyTest() {
        assertFalse(registerServiceImpl.validateUniqueUsername(""))
    }

    @Test
    fun validateUniqueUsernameBlankTest() {
        assertFalse(registerServiceImpl.validateUniqueUsername("   "))
    }

    @Test
    fun validateUniqueUsernameTabTest() {
        assertFalse(registerServiceImpl.validateUniqueUsername("\t"))
    }

    @Test
    fun validateUniqueUsernameNewLineTest() {
        assertFalse(registerServiceImpl.validateUniqueUsername("\n"))
    }

    @Test
    fun validateUniqueEmailOneSuccessTest() {
        assertTrue(registerServiceImpl.validateUniqueEmail(testAccount1.email))
    }

    @Test
    fun validateUniqueEmailTwoSuccessTest() {
        assertTrue(registerServiceImpl.validateUniqueEmail(testAccount1.email))
        assertTrue(registerServiceImpl.validateUniqueEmail(testAccount2.email))
    }

    @Test
    fun validateUniqueEmailInvalidTest() {
        assertFalse(registerServiceImpl.validateUniqueEmail(currentAccount.email))
    }

    @Test
    fun validateUniqueEmailEmptyTest() {
        assertFalse(registerServiceImpl.validateUniqueEmail(""))
    }

    @Test
    fun validateUniqueEmailBlankTest() {
        assertFalse(registerServiceImpl.validateUniqueEmail("   "))
    }

    @Test
    fun validateUniqueEmailTabTest() {
        assertFalse(registerServiceImpl.validateUniqueEmail("\t"))
    }

    @Test
    fun validateUniqueEmailNewLineTest() {
        assertFalse(registerServiceImpl.validateUniqueEmail("\n"))
    }

}