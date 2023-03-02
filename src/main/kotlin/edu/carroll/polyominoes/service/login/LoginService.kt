package edu.carroll.polyominoes.service.login

import edu.carroll.polyominoes.web.form.LoginForm
import org.springframework.security.core.userdetails.UserDetailsService


interface LoginService {
    fun validateUser(loginForm: LoginForm) : Boolean
}