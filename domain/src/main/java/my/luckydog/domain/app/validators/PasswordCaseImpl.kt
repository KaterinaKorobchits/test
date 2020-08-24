package my.luckydog.domain.app.validators

import java.util.regex.Pattern
import javax.inject.Inject

class PasswordCaseImpl @Inject constructor() : PasswordCase {

    init {
        println("!!!init: PasswordCaseImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun isValid(password: String): Boolean {
        return Pattern.compile("[\\S]{4,}").matcher(password).matches()
    }
}