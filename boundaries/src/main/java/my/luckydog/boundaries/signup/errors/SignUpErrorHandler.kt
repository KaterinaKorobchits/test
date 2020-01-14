package my.luckydog.boundaries.signup.errors

interface SignUpErrorHandler {

    fun process(t: Throwable): SignUpError
}