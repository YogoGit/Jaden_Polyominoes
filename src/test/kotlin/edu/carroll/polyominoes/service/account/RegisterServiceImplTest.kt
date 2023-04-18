package edu.carroll.polyominoes.service.account

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@SpringBootTest
internal class RegisterServiceImplTest(
        @Autowired private val registerServiceImpl: RegisterServiceImpl
) {

    companion object {

        private val testAccount0 = TestingAccount("testing.user", "test@carroll.edu", "testing123!") //existing
        private val testAccount1 = TestingAccount("testing.user1", "test1@carroll.edu", "testing456!")
        private val testAccount2 = TestingAccount("testing.user2", "test2@gmail.com", "testing7890!")
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
    fun createUserOneUserSuccessTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount1.email))
    }


    @Test
    fun createUserTwoUserSuccessTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))

        // testAccount1
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount1.email))

        // testAccount2
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount2.username))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount2.email))
    }

    @Test
    fun createUserUsernameExistsTest() {
        assertTrue(registerServiceImpl.createUser(testAccount0.username, testAccount0.email, testAccount0.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount0.username, testAccount1.email, testAccount1.rawPassword))
    }


    @Test
    fun createUserEmailExistsTest() {
        assertTrue(registerServiceImpl.createUser(testAccount0.username, testAccount0.email, testAccount0.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount0.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserDoubleCreationTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
    }

    @Test
    fun createUserTwoUserDoubleCreationTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
        assertFalse(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
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
    fun validateUsernameExistOneSuccessTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
    }

    @Test
    fun validateUsernameExistTwoSuccessTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount2.username))
    }

    @Test
    fun validateUsernameExistDuplicateSuccessTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
    }

    @Test
    fun validateUsernameExistTwoDuplicateSuccessTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount2.username))
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount2.username))
    }

    @Test
    fun validateUsernameExistFailsAfterUsernameExistsTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount1.username))
    }

    @Test
    fun validateUsernameExistFailsAfterUsernamesExistsTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertFalse(registerServiceImpl.validateUsernameExist(testAccount2.username))
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount2.username))
    }

    @Test
    fun validateUsernameExistInvalidTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount1.username))
    }

    @Test
    fun validateUsernameExistTwoUsernamesInvalidTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount1.username))
        assertTrue(registerServiceImpl.validateUsernameExist(testAccount2.username))
    }

    @Test
    fun validateUsernameExistEmptyTest() {
        assertFalse(registerServiceImpl.validateUsernameExist(""))
    }

    @Test
    fun validateUsernameExistBlankTest() {
        assertFalse(registerServiceImpl.validateUsernameExist("   "))
    }

    @Test
    fun validateUsernameExistTabTest() {
        assertFalse(registerServiceImpl.validateUsernameExist("\t"))
    }

    @Test
    fun validateUsernameExistUsernameNewLineTest() {
        assertFalse(registerServiceImpl.validateUsernameExist("\n"))
    }

    @Test
    fun validateEmailExistEmailOneSuccessTest() {
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
    }

    @Test
    fun validateEmailExistTwoSuccessTest() {
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertFalse(registerServiceImpl.validateEmailExist(testAccount2.email))
    }

    @Test
    fun validateEmailExistDuplicateSuccessTest() {
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
    }

    @Test
    fun validateEmailExistTwoDuplicateSuccessTest() {
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertFalse(registerServiceImpl.validateEmailExist(testAccount2.email))
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertFalse(registerServiceImpl.validateEmailExist(testAccount2.email))
    }

    @Test
    fun validateEmailExistFailsAfterUsernameExistsTest() {
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount1.email))
    }

    @Test
    fun validateEmailExistFailsAfterEmailsExistsTest() {
        assertFalse(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertFalse(registerServiceImpl.validateEmailExist(testAccount2.email))
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount2.email))
    }

    @Test
    fun validateEmailExistInvalidTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount1.email))
    }

    @Test
    fun validateEmailExistTwoEmailsInvalidTest() {
        assertTrue(registerServiceImpl.createUser(testAccount1.username, testAccount1.email, testAccount1.rawPassword))
        assertTrue(registerServiceImpl.createUser(testAccount2.username, testAccount2.email, testAccount2.rawPassword))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount1.email))
        assertTrue(registerServiceImpl.validateEmailExist(testAccount2.email))
    }

    @Test
    fun validateEmailExistEmptyTest() {
        assertFalse(registerServiceImpl.validateEmailExist(""))
    }

    @Test
    fun validateEmailExistBlankTest() {
        assertFalse(registerServiceImpl.validateEmailExist("   "))
    }

    @Test
    fun validateEmailExistTabTest() {
        assertFalse(registerServiceImpl.validateEmailExist("\t"))
    }

    @Test
    fun validateEmailExistNewLineTest() {
        assertFalse(registerServiceImpl.validateEmailExist("\n"))
    }

}