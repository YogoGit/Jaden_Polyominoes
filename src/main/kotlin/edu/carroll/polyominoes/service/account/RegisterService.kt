package edu.carroll.polyominoes.service.account

interface RegisterService {
    /**
     * Given a username, email, and rawPassword, creates a new account in the database.
     *
     * @param username: the valid username representing the user's account
     * @param email: a valid email to be linked to the user's account
     * @param rawPassword: a rawPassword for the user's account which will be hashed before storing
     * @return true if an account was added to the database, false otherwise
     */
    fun createUser(username: String, email: String, rawPassword: String): Boolean

    /**
     * Given an email, determine if the user's given email already exist in the database.
     *
     * @param email - an email which represents the user account
     * @return true if the user's given email is in the database, false otherwise
     */
    fun validateEmailExist(email: String): Boolean

    /**
     * Given a username, determine if the user's given username already exist in the database.
     *
     * @param username - An username representing a user's account
     * @return true if the user's given username in the database, false otherwise
     */
    fun validateUsernameExist(username: String): Boolean


}