package edu.carroll.polyominoes.service.account

interface RegisterService {

    /**
     * Given a username, determine if the user's given username does not already exist in the database.
     *
     * @param username - An username representing a user's account
     * @return true if the user's given username is not in the database, false otherwise
     */
    fun validateUniqueUsername(username: String): Boolean
    /**
     * Given an email, determine if the user's given email does not already exist in the database.
     *
     * @param email - an email which represents the user account
     * @return true if the user's given email is not in the database, false otherwise
     */
    fun validateUniqueEmail(email: String): Boolean
    /**
     * Given a username, email, and rawPassword, creates a new account in the database.
     *
     * @param username: the valid username representing the user's account
     * @param email: a valid email to be linked to the user's account
     * @param rawPassword: a rawPassword for the user's account which will be hashed before storing
     * @return true if an account was added to the database, false otherwise
     */
    fun createUser(username: String, email: String, rawPassword: String): Boolean

}