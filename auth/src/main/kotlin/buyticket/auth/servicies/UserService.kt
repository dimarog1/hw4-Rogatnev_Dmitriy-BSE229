package buyticket.auth.servicies

import buyticket.auth.dto.ApiException
import buyticket.auth.dto.UserResponseDto
import buyticket.auth.repositories.UserRepository
import buyticket.auth.servicies.SessionService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Сервис для аутентификации и регистрации пользователей.
 * Предоставляет методы для регистрации нового пользователя и входа в систему существующего пользователя.
 */
@Service
class UserService(
    private val userRepository: UserRepository,
    private val sessionService: SessionService,
) {
    /**
     * Метод для получения информации о пользователе.
     *
     * @param auth Токен авторизации.
     * @return DTO с информацией о пользователе.
     */
    fun getInfo(auth: String): UserResponseDto {
        println(auth)
        if (auth.isBlank()) {
            throw ApiException(code = 401, message = "Unauthorized.")
        }

        println(auth)
        val token = auth.replace("Bearer ", "")
        println(token)
        val session = sessionService.findByToken(token) ?: throw ApiException(
            code = 400,
            message = "Unauthorized."
        )

        val user = session.user?.let { findByNickname(it.nickname) } ?: throw ApiException(
            code = 400,
            message = "User not found."
        )

        return UserResponseDto(
            message = "Successfully.",
            nickName = user.nickname,
            email = user.email,
            id = user.id,
        )
    }

    fun findByNickname(nickname: String): buyticket.auth.models.User? {
        return userRepository.findByNickname(nickname)
    }

    fun findByEmail(email: String): buyticket.auth.models.User? {
        return userRepository.findByEmail(email)
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun save(user: buyticket.auth.models.User): buyticket.auth.models.User {
        return userRepository.save(user)
    }
}