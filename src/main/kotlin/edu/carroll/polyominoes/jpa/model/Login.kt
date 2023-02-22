package edu.carroll.polyominoes.jpa.model

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
class Login() {
    companion object {
        private val serialVersionUID: Long = 1L;
        private val EOL: String = System.lineSeparator()
        private val TAB: String = "\t"
    }

    constructor(username: String, rawPassword: String) : this() {
        this.username = username
        setRawPassword(rawPassword)
    }

    private fun setRawPassword(rawPassword: String) {

    }

    @Id
    @GeneratedValue
    var id: Int? = null

    @Column(name = "username", nullable = false, unique = true)
    var username: String? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String?= null

    @Column(name = "password", nullable = false)
    var hashPassword: String? = null

    override fun toString() : String {
        return "Login @ ${super.toString()} [ $EOL" +
                "$TAB username=$username $EOL" +
                "$TAB hashPassword=**** $EOL" +
                "]"
    }

    override fun equals(o: Any?): Boolean {
        if (this == o) return false;
        if (o == null || o::class != this::class) {
            return false;
        }
        val login : Login = o as Login;
        return (username.equals(login.username) || email.equals(login.email)) && hashPassword.equals(login.hashPassword);
    }



}