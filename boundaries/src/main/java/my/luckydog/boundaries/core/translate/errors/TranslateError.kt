package my.luckydog.boundaries.core.translate.errors

sealed class TranslateError {

    object BlockedApiKey : TranslateError()

    object InvalidApiKey : TranslateError()

    object ExceededTextSize : TranslateError()

    object ExceededDailyLimit : TranslateError()

    object TextNotTranslated : TranslateError()

    object TranslationNotSupported : TranslateError()

    object Unknown : TranslateError()
}