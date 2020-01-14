package my.luckydog.boundaries.signup.errors

sealed class SignUpError {

    data class EmailExist(val message: String) : SignUpError()

    data class EmailFormat(val message: String) : SignUpError()

    data class PasswordFormat(val message: String) : SignUpError()

    data class CredentialsFormat(val emailMsg: String, val passwordMsg: String) : SignUpError()

    data class Unknown(val t: Throwable, val message: String) : SignUpError()
}