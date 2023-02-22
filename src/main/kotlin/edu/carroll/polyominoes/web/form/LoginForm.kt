package edu.carroll.polyominoes.web.form

import jakarta.validation.constraints.NotBlank

class LoginForm() {

    @field:NotBlank(message = "Enter an username")
    var username: String = ""

    @field:NotBlank(message = "Enter a password")
    var password: String = ""

    constructor(username: String, password: String) : this() {
        this.username = username;
        this.password = password;
    }
}