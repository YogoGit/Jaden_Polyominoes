package edu.carroll.polyominoes.service.account.model

import org.springframework.security.crypto.bcrypt.BCrypt

class TestingAccount {
    var username: String
    var email: String
    var rawPassword: String

    constructor(username : String, email : String, rawPassword : String) {
        this.username = username
        this.email = email
        this.rawPassword = rawPassword
    }
}