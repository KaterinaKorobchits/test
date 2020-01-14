package my.luckydog.boundaries.signin.errors

interface SignInErrorHandler {

    fun process(t: Throwable): SignInError
}