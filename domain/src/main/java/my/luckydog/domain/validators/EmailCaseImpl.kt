package my.luckydog.domain.validators

import org.apache.commons.validator.routines.EmailValidator

class EmailCaseImpl : EmailCase {

    override fun isValid(email: String) = EmailValidator.getInstance().isValid(email)
}