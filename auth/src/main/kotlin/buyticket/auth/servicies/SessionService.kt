package buyticket.auth.servicies

import buyticket.auth.repositories.SessionRepository
import org.springframework.stereotype.Service

/**
 * Сервис для аутентификации и регистрации пользователей.
 * Предоставляет методы для регистрации нового пользователя и входа в систему существующего пользователя.
 */
@Service
class SessionService(private val sessionRepository: SessionRepository) {
    fun findByUserId(userId: Int): buyticket.auth.models.Session? {
        return sessionRepository.findByUserId(userId)
    }

    fun findByToken(token: String) : buyticket.auth.models.Session? {
        return sessionRepository.findByToken(token)
    }

    fun existsByToken(token: String) : Boolean {
        return sessionRepository.existsByToken(token)
    }

    fun save(session: buyticket.auth.models.Session): buyticket.auth.models.Session {
        return sessionRepository.save(session)
    }
}