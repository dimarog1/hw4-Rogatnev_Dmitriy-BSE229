package buyticket.tickets.repositories

import buyticket.tickets.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Int> {
    fun findAllByUserId(userId: Int): List<Order>

    fun findAllByStatus(status: Int): List<Order>
}