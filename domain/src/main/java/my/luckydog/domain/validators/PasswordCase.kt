package my.luckydog.domain.validators

interface PasswordCase {

    fun isValid(password: String): Boolean
}