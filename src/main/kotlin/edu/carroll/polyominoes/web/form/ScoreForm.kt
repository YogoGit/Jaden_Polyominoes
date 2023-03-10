package edu.carroll.polyominoes.web.form

import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class ScoreForm() {


    constructor(score: Long, polyominoes: Long, milliseconds: Long) : this() {
        this.score = score
        this.polyominoes = polyominoes
        this.milliseconds = milliseconds
    }

    @field:NotNull(message = "Please enter a score")
    @field:Digits(fraction = 0, integer = 50, message = "Score must not be a fraction and less than 50 digits")
    @field:Min(0, message = "Score must be greater than 0")
    var score: Long = 0

    @field:NotNull(message = "Please enter the number of polyominoes played")
    @field:Digits(fraction = 0, integer = 50, message = "Number must not be a fraction and less than 50 digits")
    @field:Min(0, message = "Polyominoes must be greater than 0")
    var polyominoes: Long = 0

    @field:NotNull(message = "Please enter a time in milliseconds")
    @field:Digits(fraction = 0, integer = 50, message = "Number must not be a fraction and less than 50 digits")
    @field:Min(0, message = "Time must be greater than 0")
    var milliseconds: Long = 0


}