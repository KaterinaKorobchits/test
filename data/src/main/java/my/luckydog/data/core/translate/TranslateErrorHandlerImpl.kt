package my.luckydog.data.core.translate

import my.luckydog.boundaries.core.translate.errors.TranslateError.*
import my.luckydog.boundaries.core.translate.errors.TranslateErrorHandler
import my.luckydog.boundaries.core.translate.errors.TranslateFail
import javax.inject.Inject

class TranslateErrorHandlerImpl @Inject constructor() : TranslateErrorHandler {

    private companion object {
        private const val ERROR_401 = "401"
        private const val ERROR_402 = "402"
        private const val ERROR_404 = "404"
        private const val ERROR_413 = "413"
        private const val ERROR_422 = "422"
        private const val ERROR_501 = "501"
    }

    init {
        println("!!!init: SignInErrorHandlerImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun process(t: Throwable) = if (t is TranslateFail) when (t.code) {
        ERROR_401 -> {
            InvalidApiKey}
        ERROR_402 -> BlockedApiKey
        ERROR_404 -> ExceededDailyLimit
        ERROR_413 -> ExceededTextSize
        ERROR_422 -> TextNotTranslated
        ERROR_501 -> TranslationNotSupported
        else -> Unknown
    } else Unknown
}