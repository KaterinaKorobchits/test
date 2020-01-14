package my.luckydog.data.signin

import android.content.res.Resources
import my.luckydog.boundaries.signin.errors.*
import my.luckydog.boundaries.signin.errors.SignInError.*
import my.luckydog.data.R

class SignInErrorHandlerImpl(private val resources: Resources) : SignInErrorHandler {

    override fun process(t: Throwable) = when (t) {
        is CredentialsNotExists -> CredentialsIncorrect(resources.getString(R.string.incorrect_credentials))
        is CredentialsFormatFail -> {
            val emailMsg = resources.getString(R.string.email_format_error)
            val passwordMsg = resources.getString(R.string.password_format_error)
            CredentialsFormat(emailMsg, passwordMsg)
        }
        is PasswordFormatFail -> PasswordFormat(resources.getString(R.string.password_format_error))
        is EmailFormatFail -> EmailFormat(resources.getString(R.string.email_format_error))
        else -> Unknown(t, resources.getString(R.string.unknown_error))
    }
}