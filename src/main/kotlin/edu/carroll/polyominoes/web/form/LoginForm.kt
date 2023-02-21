package edu.carroll.polyominoes.web.form

import jakarta.validation.constraints.NotBlank

class LoginForm() {

    @field:NotBlank
    var username: String = "";

    @field:NotBlank
    var password: String = "";

    constructor(username: String, password: String) : this() {
        this.username = username;
        this.password = password;
    }
}