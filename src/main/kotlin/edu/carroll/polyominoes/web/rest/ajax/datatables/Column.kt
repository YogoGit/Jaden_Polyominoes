package edu.carroll.polyominoes.web.rest.ajax.datatables

class Column() {

    constructor(data: String) : this() {
        this.data = data
    }

    constructor(data: String, name: String, orderable: Boolean) : this() {
        this.data = data
        this.name = name
        this.orderable = orderable
    }

    var data: String? = null
    var name: String? = null
    var orderable: Boolean? = null
}