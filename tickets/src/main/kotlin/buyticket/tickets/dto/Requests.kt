package buyticket.tickets.dto

data class OrderDto(
    val fromStation: String,
    val toStation: String,
)

data class StationDto(
    val station: String = "",
)