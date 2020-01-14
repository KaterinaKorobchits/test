package my.luckydog.data.signup

import android.content.res.Resources
import my.luckydog.boundaries.signup.errors.*
import my.luckydog.boundaries.signup.errors.SignUpError.*
import my.luckydog.data.R

class SignUpErrorHandlerImpl(private val resources: Resources) : SignUpErrorHandler {

    override fun process(t: Throwable) = when (t) {
        is EmailAlreadyExist -> EmailExist(resources.getString(R.string.email_exist_error))
        is CredentialsFormatFail -> {
            val emailMsg = resources.getString(R.string.email_format_error)
            val passwordMsg = resources.getString(R.string.password_format_error)
            CredentialsFormat(emailMsg, passwordMsg)
        }
        is EmailFormatFail -> EmailFormat(resources.getString(R.string.email_format_error))
        is PasswordFormatFail -> PasswordFormat(resources.getString(R.string.password_format_error))
        else -> Unknown(t, resources.getString(R.string.unknown_error))
    }
}