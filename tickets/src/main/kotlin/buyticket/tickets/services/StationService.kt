package buyticket.tickets.services

import buyticket.tickets.dto.ApiException
import buyticket.tickets.dto.StationDto
import buyticket.tickets.dto.StationResponseDto
import buyticket.tickets.dto.StationsInfoResponseDto
import buyticket.tickets.models.Station
import buyticket.tickets.repositories.StationRepository
import org.springframework.stereotype.Service

/**
 * Метод для получения информации о конкретном заказе.
 *
 * @param auth токен авторизации.
 * @param orderId идентификатор заказа.
 * @return информация о заказе.
 */
@Service
class StationService(
    private val stationRepository: StationRepository,
    private val authService: AuthService,
) {
    /**
     * Метод для создания станции.
     *
     * @param auth токен авторизации.
     * @param payload данные станции.
     * @return информация о созданной станции.
     */
    fun createStation(auth: String, payload: StationDto): StationResponseDto {
        val token = auth.replace("Bearer ", "")
        authService.isAuthorised(token)

        if (payload.station.isEmpty()) {
            throw ApiException(400, "Station name is required")
        }

        if (stationRepository.existsByStation(payload.station)) {
            throw ApiException(400, "Station already exists")
        }

        val station = Station(station = payload.station)
        val savedStation = stationRepository.save(station)

        return StationResponseDto(
            message = "Station created successfully. Station id: ${savedStation.id}. " +
                    "Station name: ${savedStation.station}."
        )
    }

    /**
     * Метод для получения всех станций.
     *
     * @return информация о всех станциях.
     */
    fun getStations(): StationsInfoResponseDto {
        val stations = stationRepository.findAll()
        return StationsInfoResponseDto(
            message = "Stations found",
            stations = stations,
        )
    }
}