package buyticket.tickets.controllers

import buyticket.tickets.dto.*
import buyticket.tickets.services.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/create")
    fun createOrder(
        @RequestHeader("Authorization", required = false) authToken: String?,
        @RequestHeader(required = false) authHeader: String?,
        @RequestBody payload: OrderDto
    ): ResponseEntity<Any> {
        try {
            val auth: String = authToken
                ?: (authHeader ?: return ResponseEntity.status(400).body("Token is empty."))
            val response = orderService.createOrder(payload, auth)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @GetMapping("/getOrders")
    fun getOrders(
        @RequestHeader("Authorization", required = false) authToken: String?,
        @RequestHeader(required = false) authHeader: String?,
    ): ResponseEntity<Any> {
        try {
            val auth: String = authToken
                ?: (authHeader ?: return ResponseEntity.status(400).body("Token is empty."))
            val response = orderService.getOrders(auth)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @GetMapping("/getOrder")
    fun getOrder(
        @RequestHeader("Authorization", required = false) authToken: String?,
        @RequestHeader(required = false) authHeader: String?,
        @RequestParam orderId: Int,
    ): ResponseEntity<Any> {
        try {
            val auth: String = authToken
                ?: (authHeader ?: return ResponseEntity.status(400).body("Token is empty."))
            val response = orderService.getOrder(auth, orderId)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }
}