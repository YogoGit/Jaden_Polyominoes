package edu.carroll.polyominoes.service

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.web.form.RegisterForm
import edu.carroll.polyominoes.jpa.repo.RegisterRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(private val registerRepo: RegisterRepository) : RegisterService {

    companion object {
        private val log : Logger = LoggerFactory.getLogger(LoginServiceImpl::class.java)
    }

    /**
     * Given a registerForm, determine if the password provided match.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the passwords match, false otherwise
     */
    override fun validateConfirmPassword(registerForm : RegisterForm) : Boolean {
        if(registerForm.password != registerForm.passwordConfirm) {
            log.info("validateConfirmPassword: passwords !match");
            return false
        }

        log.info("validateConfirmPassword: passwords match");
        return true
    }
    /**
     * Given a registerForm, determine if the user's given username does not already exist in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the user's given username is not in the database, false otherwise
     */
    override fun validateUniqueUsername(registerForm: RegisterForm) : Boolean {
        log.info("validateUniqueUsername: Checking if ${registerForm.username} exists as a user");
        val users : List<Account> = registerRepo.findByUsernameIgnoreCase(registerForm.username);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size != 0) {
            log.debug("validateUniqueUsername: found ${users.size} users");
            return false
        }

        log.debug("validateUniqueUsername: ${registerForm.username} is available");
        return true
    }

    /**
     * Given a registerForm, determine if the user's given email does not already exist in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if the user's given email is not in the database, false otherwise
     */
    override fun validateUniqueEmail(registerForm: RegisterForm): Boolean {
        log.info("validateUniqueEmail: Checking if ${registerForm.email} is linked with a user");
        val emails : List<Account> = registerRepo.findByEmailIgnoreCase(registerForm.email);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (emails.size != 0) {
            log.debug("validateUniqueEmail: found ${emails.size} emails");
            return false
        }

        log.debug("validateUniqueEmail: ${registerForm.email} is available");
        return true
    }

    /**
     * Given a registerForm, creates a new account in the database.
     *
     * @param registerForm - Data containing user Register information, such as username, email, and password.
     * @return true if an account was added to the database, false otherwise
     */
    override fun createUser(registerForm: RegisterForm): Boolean {
        if (!(validateUniqueUsername(registerForm) && validateUniqueEmail(registerForm) && validateConfirmPassword(registerForm))) {
             log.debug("createUser: Username or email was not unique or passwords !match");
             return false;
            }
        val hashPassword = BCrypt.hashpw(registerForm.password, BCrypt.gensalt())
        val user: Account = Account(registerForm.username, registerForm.email, hashPassword)
        registerRepo.save(user)

        log.info("createUser: Created an account for ${registerForm.username}")
        return true;
    }

}