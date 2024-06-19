package buyticket.auth.jwt

import buyticket.auth.models.User
import com.auth0.jwt.JWT
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.beans.factory.annotation.Value
import javax.crypto.SecretKey

/**
 * Сервис для работы с JWT (JSON Web Tokens).
 * Предоставляет методы для проверки валидности токена и генерации нового токена.
 */
@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secretKey: String
) {
    /**
     * Метод для поиска пользователя по никнейму.
     *
     * @param nickname Никнейм пользователя.
     * @return Пользователь или null, если пользователь не найден.
     */
    fun isValid(token: String): Boolean {
        try {
            val decodedToken = JWT.decode(token)
            val expirationDate = Date(decodedToken.expiresAt.time)
            return expirationDate.after(Date())
        } catch (e: Exception) {
            return false
        }
    }

    /**
     * Метод для генерации токена.
     *
     * @param user Пользователь, для которого генерируется токен.
     * @return Сгенерированный токен.
     */
    fun generateToken(user: User): String {
        return Jwts.builder().subject(user.id.toString())
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(signingKey())
            .compact()
    }

    private fun signingKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}