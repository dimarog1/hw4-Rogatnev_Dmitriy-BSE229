package buyticket.auth.servicies

import buyticket.auth.jwt.JwtService
import org.springframework.stereotype.Service

/**
 * Сервис для валидации токенов.
 * Предоставляет метод для проверки валидности токена.
 */
@Service
class ValidateTokenService(
    private val jwtService: JwtService,
    private val sessionService: SessionService,
) {
    /**
     * Метод для поиска пользователя по никнейму.
     *
     * @param nickname Никнейм пользователя.
     * @return Пользователь или null, если пользователь не найден.
     */
    fun validateToken(token: String): Boolean {
        return sessionService.existsByToken(token) && jwtService.isValid(token)
    }
}