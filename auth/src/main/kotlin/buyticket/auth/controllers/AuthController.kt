package buyticket.auth.controllers

import buyticket.auth.dto.*
import buyticket.auth.servicies.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthService,
    private val validateTokenService: ValidateTokenService,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody payload: LoginDto
    ): ResponseEntity<Any> {
        try {
            val response = authenticationService.login(payload)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @PostMapping("/register")
    fun register(
        @RequestBody payload: RegisterDto
    ): ResponseEntity<Any> {
        try {
            val response = authenticationService.register(payload)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }

    @PostMapping("/validateToken")
    fun validateToken(
        @RequestBody payload: TokenDto,
    ): ResponseEntity<Any> {
        try {
            val isValid = validateTokenService.validateToken(payload.token)
            if (isValid) {
                return ResponseEntity.ok()
                    .body(TokenResponseDto(isValid = true, message = "Token is valid."))
            }
            return ResponseEntity.ok().body(TokenResponseDto(isValid = false, message = "Token is not valid."))
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }
}