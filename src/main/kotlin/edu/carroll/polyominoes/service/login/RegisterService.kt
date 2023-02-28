package edu.carroll.polyominoes.service.login

import edu.carroll.polyominoes.web.form.RegisterForm

interface RegisterService {

    fun validateUniqueUsername(registerForm: RegisterForm) : Boolean
    fun validateUniqueEmail(registerForm: RegisterForm) : Boolean
    fun createUser(registerForm: RegisterForm) : Boolean

}