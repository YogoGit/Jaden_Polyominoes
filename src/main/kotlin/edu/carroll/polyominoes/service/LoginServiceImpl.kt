package edu.carroll.polyominoes.service

import edu.carroll.polyominoes.jpa.model.Account
import edu.carroll.polyominoes.jpa.repo.LoginRepository
import edu.carroll.polyominoes.web.form.LoginForm
import org.springframework.stereotype.Service
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt

@Service
class LoginServiceImpl(private val loginRepo: LoginRepository) : LoginService {

    companion object {
        private val log : Logger = LoggerFactory.getLogger(LoginServiceImpl::class.java)
    }


    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param loginForm - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    override fun validateUser(loginForm: LoginForm): Boolean {
        log.info("validateUser: user ${loginForm.username} attempted login");

        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        var usersByUsername : List<Account> = loginRepo.findByUsernameIgnoreCase(loginForm.username);
        var usersByEmail : List<Account> = loginRepo.findByEmailIgnoreCase(loginForm.username);
        var users : List<Account> = usersByUsername + usersByEmail


        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size != 1) {
            log.debug("validateUser: found ${users.size} users");
            return false
        }

        val user : Account = users[0]


        if (!BCrypt.checkpw(loginForm.password,user.hashPassword)) {
            log.debug("validateUser: password !match");
            return false;
        }

        // User exists, and the provided password matches the hashed password in the database.
        log.debug("validateUser: successful login");
        return true

    }


}