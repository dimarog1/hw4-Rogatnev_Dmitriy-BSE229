package buyticket.auth.servicies

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

/**
 * Сервис для аутентификации и регистрации пользователей.
 * Предоставляет методы для регистрации нового пользователя и входа в систему существующего пользователя.
 */
@Service
class HashService {
    /**
     * Метод для проверки хеша пароля.
     *
     * @param input Введенный пароль.
     * @param hash Хеш пароля.
     * @return true, если хеш соответствует введенному паролю, иначе false.
     */
    fun checkBcrypt(input: String, hash: String): Boolean {
        return BCrypt.checkpw(input, hash)
    }

    /**
     * Метод для проверки хеша пароля.
     *
     * @param input Введенный пароль.
     * @param hash Хеш пароля.
     * @return true, если хеш соответствует введенному паролю, иначе false.
     */
    fun hashBcrypt(input: String): String {
        return BCrypt.hashpw(input, BCrypt.gensalt(10))
    }
}