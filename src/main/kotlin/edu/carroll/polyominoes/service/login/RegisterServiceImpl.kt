package edu.carroll.polyominoes.service.login

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.web.form.RegisterForm
import edu.carroll.polyominoes.jpa.repo.RegisterRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(private val registerRepo: RegisterRepository) : RegisterService {

    companion object {
        private val log  = LoggerFactory.getLogger(RegisterServiceImpl::class.java)
    }

    /**
     * Given a registerForm, determine if the user's given username does not already exist in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the user's given username is not in the database, false otherwise
     */
    override fun validateUniqueUsername(registerForm: RegisterForm) : Boolean {
        log.info("validateUniqueUsername: Checking if '{}' is linked to an account", registerForm.username);
        val users  = registerRepo.findByUsernameIgnoreCase(registerForm.username);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size != 0) {
            if (users.size > 1) {
                log.warn("validateUniqueUsername: found {} users for '{}'", users.size,registerForm.username);
            } else {
                log.debug("validateUniqueUsername: found 1 user for '{}'", registerForm.username);
            }
            return false
        }

        log.debug("validateUniqueUsername: '{}' is available", registerForm.username);
        return true
    }

    /**
     * Given a registerForm, determine if the user's given email does not already exist in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the user's given email is not in the database, false otherwise
     */
    override fun validateUniqueEmail(registerForm: RegisterForm): Boolean {
        log.info("validateUniqueEmail: Checking if '{}' is linked to an account", registerForm.email);
        val emails = registerRepo.findByEmailIgnoreCase(registerForm.email);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (emails.size != 0) {
            if (emails.size > 1) {
                log.warn("validateUniqueEmail: found {} emails for '{}'", emails.size, registerForm.email)
            } else {
                log.debug("validateUniqueEmail: found 1 email for '{}'", registerForm.email)
            }
            return false
        }

        log.debug("validateUniqueEmail: '{}' is available", registerForm.email);
        return true
    }

    /**
     * Given a registerForm, creates a new account in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if an account was added to the database, false otherwise
     */
    override fun createUser(registerForm: RegisterForm): Boolean {
        if (!(validateUniqueUsername(registerForm) && validateUniqueEmail(registerForm))) {
             log.warn("createUser: function was called when username or email was not unique");
             return false;
            }
        val hashPassword = BCrypt.hashpw(registerForm.password, BCrypt.gensalt())
        val user: Account = Account(registerForm.username, registerForm.email, hashPassword)
        registerRepo.save(user)

        log.info("createUser: Created an account for ${registerForm.username}")
        return true;
    }

}