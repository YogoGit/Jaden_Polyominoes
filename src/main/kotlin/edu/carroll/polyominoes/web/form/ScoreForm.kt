package edu.carroll.polyominoes.web.form

import jakarta.validation.constraints.Digits

class ScoreForm() {


    constructor(score: String, polyominoes: String, timeInMillis: String) : this() {
        this.score = score
        this.polyominoes = polyominoes
        this.time = time
    }

    @field:Digits(fraction = 0, integer = 50)
    var score = ""

    @field:Digits(fraction = 0, integer = 50)
    var polyominoes = ""

    @field:Digits(fraction = 0, integer = 50)
    var time = ""


}