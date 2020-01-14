package my.luckydog.domain.validators

interface EmailCase {

    fun isValid(email: String): Boolean
}