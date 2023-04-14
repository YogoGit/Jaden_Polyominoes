package edu.carroll.polyominoes.web.rest.ajax

import edu.carroll.polyominoes.web.rest.ajax.datatables.Column
import edu.carroll.polyominoes.web.rest.ajax.datatables.Order
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * Represents the ajax request for Datatables.js
 *
 * @see <a href="https://datatables.net/manual/server-side/">Datatables Server Side Documentation</a>
 * @see <a href="https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-datatable">Frontbackend Spring Boot + Bootstrap + Thymeleaf Datatable</a>
 */
class DatatablesRequest() {

    constructor(draw: Int, start: Int, length: Int, order: List<Order>, column: List<Column>) : this() {
        this.draw = draw
        this.start = start
        this.length = length
        this.order = order
        this.columns = columns
    }

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var draw = 0

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var start = 0

    @field:NotNull
    @field:Size(min = 0, max = 2147483647)
    var length = 0
    var order: List<Order> = listOf<Order>()
    var columns: List<Column> = listOf<Column>()


}
