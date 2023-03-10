package edu.carroll.polyominoes.service.account

import edu.carroll.polyominoes.service.account.model.SecurityAccount
import edu.carroll.polyominoes.jpa.repo.account.LoginRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val loginRepo: LoginRepository) : UserDetailsService {
    companion object {
        private val log = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)
    }


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the `UserDetails`
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never `null`)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {

        if (username.isNullOrBlank()) {
            log.warn("loadUserByUsername: username was null")
            throw UsernameNotFoundException("username was null")
        }

        log.debug("loadUserByUsername: attempting to find user '{}'", username)

        val users = if (username.contains("@")) {
            log.debug("loadUserByUsername: username contained '@' looking up '{}' by email", username)
            // Always do the lookup in a case-insensitive manner (lower-casing the data).
            loginRepo.findByEmailIgnoreCase(username)
        } else {
            log.debug("loadUserByUsername: username was not an email looking up '{}' by username", username)
            // Always do the lookup in a case-insensitive manner (lower-casing the data).
            loginRepo.findByUsernameIgnoreCase(username)
        }

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size != 1) {
            if (users.size > 1) {
                log.warn("validateUser: found {} users for '{}'", users.size, username)
            } else {
                log.debug("validateUser: found no users for {}", username)
            }
            throw UsernameNotFoundException("Found ${users.size} users for '${username}'")
        }


        return SecurityAccount(users[0])
    }
}