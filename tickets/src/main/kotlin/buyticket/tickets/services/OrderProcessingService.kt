package buyticket.tickets.services

import buyticket.tickets.repositories.OrderRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderProcessingService(
    private val orderRepository: OrderRepository
) {
    /**
     * Метод для обработки заказов. Запускается каждые 10 секунд.
     * Извлекает все заказы со статусом "check" (1) и устанавливает случайному из них случайный статус.
     */
    @Scheduled(fixedDelay = 10000)
    fun processOrders() {
        val ordersToProcess = orderRepository.findAllByStatus(1)
        if (ordersToProcess.isEmpty()) return
        val randomOrder = ordersToProcess[Random().nextInt(ordersToProcess.size)]

        randomOrder.status = if (Random().nextBoolean()) 2 else 3
        orderRepository.save(randomOrder)
    }
}