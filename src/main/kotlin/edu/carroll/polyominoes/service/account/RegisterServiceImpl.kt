package edu.carroll.polyominoes.service.account

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.repo.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(private val accountRepo: AccountRepository) : RegisterService {

    companion object {
        private val log = LoggerFactory.getLogger(RegisterServiceImpl::class.java)
    }

    /**
     * Given a username, determine if the user's given username already exist in the database.
     *
     * @param username - An username representing a user's account
     * @return true if the user's given username is in the database, false otherwise
     */
    override fun validateUsernameExist(username: String): Boolean {

        if (username.isNullOrBlank()) {
            log.warn("validateUsernameExist: username '{}' was null or blank", username)
            return false
        }

        log.info("validateUsernameDoesNotExist: Checking if '{}' is linked to an account", username)
        val users = accountRepo.findByUsernameIgnoreCase(username)

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.isNotEmpty()) {
            if (users.size > 1) {
                log.warn("validateUsernameExist: found {} users for '{}'", users.size, username)
            } else {
                log.debug("validateUsernameExist: found 1 user for '{}'", username)
            }
            return true
        }

        log.info("validateUsernameExist: '{}' is available", username)
        return false
    }

    /**
     * Given an email, determine if the user's given email already exist in the database.
     *
     * @param email - an email which represents the user account
     * @return true if the user's given email is in the database, false otherwise
     */
    override fun validateEmailExist(email: String): Boolean {
        log.info("validateUniqueEmail: Checking if '{}' is linked to an account", email)
        val emails = accountRepo.findByEmailIgnoreCase(email)

        if (email.isNullOrBlank()) {
            log.warn("validateUniqueEmail: email '{}' was null or blank", email)
            return false
        }

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (emails.isNotEmpty()) {
            if (emails.size > 1) {
                log.warn("validateUniqueEmail found {} emails for '{}'", emails.size, email)
            } else {
                log.debug("validateUniqueEmail: found 1 email for '{}'", email)
            }
            return true
        }

        log.info("validateUniqueEmail: '{}' is available", email)
        return false
    }

    /**
     * Given a username, email, and rawPassword, creates a new account in the database.
     *
     * @param username: the valid username representing the user's account
     * @param email: a valid email to be linked to the user's account
     * @param rawPassword: a rawPassword for the user's account which will be hashed before storing
     * @return true if an account was added to the database, false otherwise
     */
    override fun createUser(username: String, email: String, rawPassword: String): Boolean {

        log.info("createUser: attempting to create user for '{}'", username);

        if (username.isNullOrBlank()) {
            log.warn("createUser: username was null or blank!")
            return false
        }

        if (email.isNullOrBlank()) {
            log.warn("createUser: email was null or blank!")
            return false
        }

        if (rawPassword.isNullOrBlank()) {
            log.warn("createUser: rawPassword was null or blank!")
            return false
        }

        if (validateUsernameExist(username)) {
            log.debug("createUser: username '{}' already exists", username)
            return false
        }

        if (validateEmailExist(email)) {
            log.debug("createUser: email '{}' already exists", email)
            return false
        }

        val hashPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt())
        val user: Account = Account(username, email, hashPassword)
        accountRepo.save(user)

        log.info("createUser: Created an account for '{}'", username)
        return true
    }

}