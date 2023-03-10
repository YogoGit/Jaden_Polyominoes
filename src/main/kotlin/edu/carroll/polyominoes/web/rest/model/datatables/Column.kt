package edu.carroll.polyominoes.web.rest.model.datatables

class Column() {

    constructor(data: String): this() {
        this.data = data
    }

    var data: String? = null
    var name: String? = null
    var orderable: Boolean? = null
}