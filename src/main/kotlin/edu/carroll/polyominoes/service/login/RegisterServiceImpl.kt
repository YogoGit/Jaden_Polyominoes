package edu.carroll.polyominoes.service.login

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.repo.RegisterRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(private val registerRepo: RegisterRepository) : RegisterService {

    companion object {
        private val log = LoggerFactory.getLogger(RegisterServiceImpl::class.java)
    }

    /**
     * Given a registerForm, determine if the user's given username does not already exist in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the user's given username is not in the database, false otherwise
     */
    override fun validateUniqueUsername(username: String): Boolean {
        log.info("validateUniqueUsername: Checking if '{}' is linked to an account", username);
        val users = registerRepo.findByUsernameIgnoreCase(username);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size != 0) {
            if (users.size > 1) {
                log.warn("validateUniqueUsername: found {} users for '{}'", users.size, username);
            } else {
                log.debug("validateUniqueUsername: found 1 user for '{}'", username);
            }
            return false
        }

        log.info("validateUniqueUsername: '{}' is available", username);
        return true
    }

    /**
     * Given a registerForm, determine if the user's given email does not already exist in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the user's given email is not in the database, false otherwise
     */
    override fun validateUniqueEmail(email: String): Boolean {
        log.info("validateUniqueEmail: Checking if '{}' is linked to an account", email);
        val emails = registerRepo.findByEmailIgnoreCase(email);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (emails.size != 0) {
            if (emails.size > 1) {
                log.warn("validateUniqueEmail: found {} emails for '{}'", emails.size, email)
            } else {
                log.debug("validateUniqueEmail: found 1 email for '{}'", email)
            }
            return false
        }

        log.info("validateUniqueEmail: '{}' is available", email);
        return true
    }

    /**
     * Given a registerForm, creates a new account in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if an account was added to the database, false otherwise
     */
    override fun createUser(username: String, email: String, rawPassword: String): Boolean {

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

        val hashPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt())
        val user: Account = Account(username, email, hashPassword)
        registerRepo.save(user)

        log.info("createUser: Created an account for {}", username)
        return true;
    }

}