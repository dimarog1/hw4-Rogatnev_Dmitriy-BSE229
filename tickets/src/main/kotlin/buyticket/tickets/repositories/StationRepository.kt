package buyticket.tickets.repositories

import buyticket.tickets.models.Station
import org.springframework.data.jpa.repository.JpaRepository

interface StationRepository : JpaRepository<Station, Int> {
    fun existsByStation(name: String): Boolean

    fun findByStation(name: String): Station?
}