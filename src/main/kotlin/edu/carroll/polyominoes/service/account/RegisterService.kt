package edu.carroll.polyominoes.service.account

interface RegisterService {

    fun validateUniqueUsername(username: String): Boolean
    fun validateUniqueEmail(email: String): Boolean
    fun createUser(username: String, email: String, rawPassword: String): Boolean

}