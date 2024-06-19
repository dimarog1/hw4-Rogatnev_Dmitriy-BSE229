package buyticket.auth.servicies;

import buyticket.auth.dto.*
import buyticket.auth.jwt.JwtService
import buyticket.auth.models.Session
import buyticket.auth.models.User
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.stereotype.Service;
import java.sql.Timestamp
import java.util.regex.Pattern

/**
 * Сервис для аутентификации и регистрации пользователей.
 * Предоставляет методы для регистрации нового пользователя и входа в систему существующего пользователя.
 */
@Service
class AuthService(
    private val userService: UserService,
    private val hashService: HashService,
    private val jwtService: JwtService,
    private val sessionService: SessionService,
) {
    /**
     * Сервис для валидации токенов.
     * Предоставляет метод для проверки валидности токена.
     */
    fun register(payload: RegisterDto): RegisterResponseDto {
        if (payload.email.isBlank() || payload.nickName.isBlank() || payload.password.isBlank()) {
            throw ApiException(400, "All fields are required")
        }

        if (userService.existsByEmail(payload.email)) {
            throw ApiException(400, "Email already exists")
        }

        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)$"
        if (!Pattern.matches(emailRegex, payload.email)) {
            throw ApiException(400, "Invalid email format")
        }

        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$"
        if (!Pattern.matches(passwordRegex, payload.password)) {
            throw ApiException(
                400, "Invalid password format. Password must contain at least one" +
                        " digit, one lower case letter, one upper case letter, one special character, no whitespace, " +
                        "and be at least 8 characters long."
            )
        }

        val user = User(
            nickname = payload.nickName,
            password = hashService.hashBcrypt(payload.password),
            email = payload.email,
        )

        val savedUser = userService.save(user)

        return RegisterResponseDto(
            message = "Registered successfully. Now login, ${savedUser.nickname}.",
        )
    }

    /**
     * Метод для входа в систему.
     *
     * @param payload DTO с данными для входа.
     * @return DTO с сообщением о результате входа и токеном.
     */
    fun login(payload: LoginDto): LoginResponseDto {
        val user = userService.findByEmail(payload.email) ?: throw ApiException(400, "Incorrect email or password")

        if (!hashService.checkBcrypt(payload.password, user.password)) {
            throw ApiException(400, "Incorrect email or password")
        }

        val token = jwtService.generateToken(user)

        var session = sessionService.findByUserId(user.id)
        if (session != null) {
            session.token = token
            session.expires = Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        } else {
            session = Session(
                user = user,
                token = token,
                expires = Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000),
            )
        }

        sessionService.save(session)

        return LoginResponseDto(
            message = "Logged in successfully. Welcome, ${user.nickname}.",
            token = token,
        )
    }
}