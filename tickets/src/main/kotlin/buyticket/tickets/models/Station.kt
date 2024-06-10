package buyticket.tickets.models

import jakarta.persistence.*

@Entity
@Table(name = "station", schema = "public")
data class Station(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0,

    @Column(name = "station")
    var station: String = "",
)