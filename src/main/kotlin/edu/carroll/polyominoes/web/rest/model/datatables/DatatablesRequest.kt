package edu.carroll.polyominoes.web.rest.ajax

import edu.carroll.polyominoes.web.rest.model.datatables.Column
import edu.carroll.polyominoes.web.rest.model.datatables.Order

/**
 * Represents the ajax request for Datatables.js
 *
 * @see <a href="https://datatables.net/manual/server-side/">Datatables Server Side Documentation</a>
 * @see <a href="https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-datatable">Frontbackend Spring Boot + Bootstrap + Thymeleaf Datatable</a>
 */
class DatatablesRequest {


    var draw: Long = 0
    var start = 0
    var length = 0
    var order: List<Order> = listOf<Order>()
    var columns: List<Column> = listOf<Column>()


}
