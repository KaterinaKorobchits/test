package my.luckydog.boundaries.signin.errors

sealed class SignInError {

    data class CredentialsIncorrect(val message: String) : SignInError()

    data class EmailFormat(val message: String) : SignInError()

    data class PasswordFormat(val message: String) : SignInError()

    data class CredentialsFormat(val emailMsg: String, val passwordMsg: String) : SignInError()

    data class Unknown(val t: Throwable, val message: String) : SignInError()
}