package edu.carroll.polyominoes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class PolyominoesApplication

fun main(args: Array<String>) {
    runApplication<PolyominoesApplication>(*args)
}
