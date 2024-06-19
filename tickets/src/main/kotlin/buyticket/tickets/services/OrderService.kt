package buyticket.tickets.services

import buyticket.tickets.dto.*
import buyticket.tickets.models.Order
import buyticket.tickets.repositories.OrderRepository
import buyticket.tickets.repositories.StationRepository
import org.springframework.stereotype.Service

/**
 * Сервис для работы с заказами.
 *
 * @property orderRepository репозиторий заказов.
 * @property stationRepository репозиторий станций.
 * @property authService сервис авторизации.
 */
@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val stationRepository: StationRepository,
    private val authService: AuthService,
) {
    /**
     * Метод для создания заказа.
     *
     * @param payload данные заказа.
     * @param auth токен авторизации.
     * @return информация о созданном заказе.
     */
    fun createOrder(payload: OrderDto, auth: String): OrderResponseDto {
        val token = auth.replace("Bearer ", "")
        authService.isAuthorised(token)

        if (payload.fromStation.isEmpty() || payload.toStation.isEmpty()) {
            throw ApiException(code = 400, message = "All fields are required")
        }

        val fromStation =
            stationRepository.findByStation(payload.fromStation) ?: throw ApiException(400, "Invalid from station name")
        val toStation =
            stationRepository.findByStation(payload.toStation) ?: throw ApiException(400, "Invalid to station name")

        if (fromStation.id == toStation.id) {
            throw ApiException(400, "From and to stations must be different")
        }

        val order = Order(
            userId = authService.getUserId(token),
            fromStation = fromStation,
            toStation = toStation,
            status = 1,
        )

        val savedOrder = orderRepository.save(order)

        return OrderResponseDto(
            message = "Order created successfully. Order id: ${savedOrder.id}."
        )
    }

    /**
     * Сервис для работы с заказами.
     *
     * @property orderRepository репозиторий заказов.
     * @property stationRepository репозиторий станций.
     * @property authService сервис авторизации.
     */
    fun getOrders(auth: String): OrdersInfoResponseDto {
        val token = auth.replace("Bearer ", "")
        authService.isAuthorised(token)

        return OrdersInfoResponseDto(
            message = "Orders found successfully.",
            orders = orderRepository.findAllByUserId(authService.getUserId(token))
        )
    }

    /**
     * Метод для получения информации о конкретном заказе.
     *
     * @param auth токен авторизации.
     * @param orderId идентификатор заказа.
     * @return информация о заказе.
     */
    fun getOrder(auth: String, orderId: Int): OrdersInfoResponseDto {
        val token = auth.replace("Bearer ", "")
        authService.isAuthorised(token)

        val order = orderRepository.findById(orderId).orElseThrow { ApiException(404, "Order not found") }
        return OrdersInfoResponseDto(
            message = "Order found successfully.",
            orders = listOf(order)
        )
    }
}