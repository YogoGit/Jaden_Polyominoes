package edu.carroll.polyominoes.web.rest.ajax.datatables

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class Order() {

    constructor(column: Int, direction: String) : this() {
        this.column = column
        this.dir = direction
    }

    @field:Size(min = 0, max = 2147483647)
    var column: Int? = 0

    @field:Pattern(regexp = "^(asc|desc)\$")
    var dir: String? = null
}