package my.luckydog.domain.app.validators

import org.apache.commons.validator.routines.EmailValidator
import javax.inject.Inject

class EmailCaseImpl @Inject constructor() : EmailCase {

    init {
        println("!!!init: EmailCaseImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun isValid(email: String) = EmailValidator.getInstance().isValid(email)
}