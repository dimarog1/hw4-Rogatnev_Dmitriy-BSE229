package buyticket.tickets.models

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "order", schema = "public")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0,

    @Column(name = "user_id")
    var userId: Int = -1,

    @ManyToOne
    @JoinColumn(name = "from_station_id", referencedColumnName = "id")
    var fromStation: Station? = null,

    @ManyToOne
    @JoinColumn(name = "to_station_id", referencedColumnName = "id")
    var toStation: Station? = null,

    @Column(name = "status")
    var status: Int = 0,

    @Column(name = "created")
    var created: Timestamp = Timestamp(System.currentTimeMillis()),
)