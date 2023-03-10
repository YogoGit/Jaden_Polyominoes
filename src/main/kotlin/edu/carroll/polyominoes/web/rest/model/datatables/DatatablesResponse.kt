package edu.carroll.polyominoes.web.rest.model.datatables

/**
 * Represents the ajax response for Datatables.js
 *
 * @see <a href="https://datatables.net/manual/server-side/">Datatables Server Side Documentation</a>
 * @see <a href="https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-datatable">Frontbackend Spring Boot + Bootstrap + Thymeleaf Datatable</a>
 */
class DatatablesResponse<T> {

    /**
     * Given a list representing data in a table creates a Datatables.
     *
     * @param data: a list objects representing one row of the table.
     */

    constructor(data: List<T>, recordsFiltered: Long, recordsTotal: Long, draw: Long) {
        this.data = data
        this.recordsFiltered = recordsFiltered
        this.recordsTotal = recordsTotal
        this.draw = draw
    }

    var data = listOf<T>()
    var recordsFiltered: Long = 0
    var recordsTotal: Long = 0
    var draw: Long = 0

}