package edu.carroll.polyominoes.web.form

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class RegisterForm() {

    @field:NotBlank(message = "Enter an username")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9.]*\$",
        message = "Usernames can contain letters (a-z), numbers (0-9), and periods (.)"
    )
    @field:Size(min = 6, max = 64, message = "Usernames must be between 6 and 64 characters")
    var username: String = ""

    @field:NotBlank(message = "Enter an email")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",
        message = "Enter a valid email"
    ) // RFC 5322 standard email regex.
    var email: String = ""

    @field:NotBlank(message = "Enter a password")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#\$%^&*()_+={}\\[\\]|\\\\:;\"'<,>.?/-]*\$",
        message = "Passwords can contain letters (a-z), numbers (0-9), and special characters (!@#\$%^&*()_+={}\\[\\]|\\\\:;\"'<,>.?/)"
    )
    @field:Size(min = 8, message = "Password must be between 8 and 128 characters")
    var rawPassword: String = ""

    @field:NotBlank(message = "Confirm your password")
    var rawPasswordConfirm: String = ""

    constructor(username: String, email: String, rawPassword: String, rawPasswordConfirm: String) : this() {
        this.username = username
        this.email = email
        this.rawPassword = rawPassword
        this.rawPassword = rawPasswordConfirm
    }

}