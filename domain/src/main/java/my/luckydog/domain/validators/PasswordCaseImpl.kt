package my.luckydog.domain.validators

import java.util.regex.Pattern

class PasswordCaseImpl : PasswordCase {

    override fun isValid(password: String): Boolean {
        return Pattern.compile("[\\S]{4,}").matcher(password).matches()
    }
}