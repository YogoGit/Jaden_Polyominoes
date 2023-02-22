package edu.carroll.polyominoes.service

import edu.carroll.polyominoes.web.form.RegisterForm

interface RegisterService {

    fun validateConfirmPassword(registerForm : RegisterForm) : Boolean
    fun validateUniqueUsername(registerForm: RegisterForm) : Boolean
    fun validateUniqueEmail(registerForm: RegisterForm) : Boolean

    fun createUser(registerForm: RegisterForm) : Boolean

}