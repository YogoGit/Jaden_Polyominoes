package edu.carroll.polyominoes.jpa.model

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
class Account() {
    companion object {
        private val serialVersionUID: Long = 1L;
        private val EOL: String = System.lineSeparator()
        private val TAB: String = "\t"
    }
    constructor(username: String, email: String, hashPassword: String) : this() {
        this.username = username
        this.email = email
        this.hashPassword = hashPassword
    }

    @Id
    @GeneratedValue
    var id: Long? = null

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
        val login : Account = o as Account;
        return (username.equals(login.username) || email.equals(login.email)) && hashPassword.equals(login.hashPassword);
    }



}