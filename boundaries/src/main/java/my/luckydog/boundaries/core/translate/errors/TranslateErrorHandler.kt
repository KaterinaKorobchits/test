package my.luckydog.boundaries.core.translate.errors

interface TranslateErrorHandler {

    fun process(t: Throwable): TranslateError
}