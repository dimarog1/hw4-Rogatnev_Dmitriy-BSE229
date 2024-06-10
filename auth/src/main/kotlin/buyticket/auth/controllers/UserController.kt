package buyticket.auth.controllers

import buyticket.auth.dto.ApiException
import buyticket.auth.dto.UserResponseDto
import buyticket.auth.jwt.JwtService
import buyticket.auth.servicies.UserService
import buyticket.auth.servicies.ValidateTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val validateTokenService: ValidateTokenService,
) {
    @GetMapping("/getInfo")
    fun getInfo(
        @RequestHeader("Authorization", required = false) authToken: String?,
        @RequestHeader(required = false) authHeader: String?,
    ): ResponseEntity<Any> {
        try {
            val auth: String = authToken
                ?: (authHeader ?: return ResponseEntity.status(400).body("Token is empty."))
            if (!validateTokenService.validateToken(auth.replace("Bearer ", ""))) {
                return ResponseEntity.status(401).body("Unauthorized.")
            }
            val response = userService.getInfo(auth)
            return ResponseEntity.ok().body(response)
        } catch (e: ApiException) {
            return ResponseEntity.status(e.code).body(e.message)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(e.message)
        }
    }
}