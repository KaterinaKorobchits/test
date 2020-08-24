package my.luckydog.domain.app.validators

interface EmailCase {

    fun isValid(email: String): Boolean
}