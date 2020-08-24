package my.luckydog.domain.app.validators

interface PasswordCase {

    fun isValid(password: String): Boolean
}