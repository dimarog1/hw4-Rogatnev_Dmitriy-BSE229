package buyticket.auth.dto

import org.springframework.web.server.ResponseStatusException

class ApiException(val code: Int, message: String) : ResponseStatusException(code, message, null)

data class LoginResponseDto(
    val message: String,
    val token: String,
)

data class RegisterResponseDto(
    val message: String,
)

data class UserResponseDto(
    val message: String,
    val nickName: String,
    val email: String,
    val id: Int,
)

data class TokenResponseDto(
    val message: String,
    val isValid: Boolean,
)