package edu.carroll.polyominoes.web.rest.model.datatables

class Order() {

    constructor(column: Int, direction: String) : this() {
        this.column = column
        this.dir = direction
    }

    var column: Int? = null
    var dir: String? = null
}