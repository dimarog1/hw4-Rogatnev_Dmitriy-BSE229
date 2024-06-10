package buyticket.tickets.dto

import buyticket.tickets.models.Order
import buyticket.tickets.models.Station
import org.springframework.web.server.ResponseStatusException

class ApiException(val code: Int, message: String) : ResponseStatusException(code, message, null)

data class OrderResponseDto(
    val message: String,
)

data class OrdersInfoResponseDto(
    val message: String,
    val orders: List<Order>,
)

data class StationResponseDto(
    val message: String,
)

data class StationsInfoResponseDto(
    val message: String,
    val stations: Iterable<Station>,
)