package edu.carroll.polyominoes.service.login

import edu.carroll.polyominoes.web.form.LoginForm


interface LoginService {
    fun validateUser(loginForm: LoginForm) : Boolean
}