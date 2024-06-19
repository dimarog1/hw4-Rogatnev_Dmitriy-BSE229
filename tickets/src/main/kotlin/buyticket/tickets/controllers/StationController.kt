package buyticket.tickets.controllers

import buyticket.tickets.dto.ApiException
import buyticket.tickets.dto.StationDto
import buyticket.tickets.services.AuthService
import buyticket.tickets.services.StationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stations")
class StationController(
    private val stationService: StationService,
) {
    @PostMapping("/create")
    fun createStation(
        @RequestHeader("Authorization", required = false) authToken: String?,
        @RequestHeader(required = false) authHeader: String?,
        @RequestBody payload: StationDto
    ): ResponseEntity<Any> {
        try {
            val auth: String = authToken
                ?: (authHeader ?: return ResponseEntity.status(400).body("Token is empty."))
            val response = stationService.createStation(auth, payload)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @GetMapping("/all")
    fun getStations(): ResponseEntity<Any> {
        try {
            val response = stationService.getStations()
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }
}