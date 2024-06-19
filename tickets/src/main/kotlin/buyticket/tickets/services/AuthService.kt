package buyticket.tickets.services

import buyticket.tickets.dto.ApiException
import com.auth0.jwt.JWT
import org.springframework.stereotype.Service

/**
 * Сервис для работы с авторизацией.
 */
@Service
class AuthService {
    private val url: String = "http://auth-app:8080/auth/validateToken"
//    private val url: String = "http://localhost:8080/auth/validateToken"

    /**
     * Сервис для работы с авторизацией.
     */
    fun isAuthorised(token: String) {
        try {
            val response = khttp.get(
                url = url,
                json = mapOf("token" to token)
            ).jsonObject
            val isValid = response["isValid"]
            if (isValid != true) {
                throw ApiException(code = 400, message = "Token is not valid.")
            }
        } catch (e: Exception) {
            throw ApiException(code = 400, message = e.message!!)
        }
    }

    /**
     * Метод для получения идентификатора пользователя из токена.
     *
     * @param token токен авторизации.
     * @return идентификатор пользователя.
     */
    fun getUserId(token: String) : Int {
        try {
            val decodedToken = JWT.decode(token)
            val userId = decodedToken.subject
            return userId.toInt()
        } catch (e: Exception) {
            throw ApiException(403, "Invalid token")
        }
    }
}