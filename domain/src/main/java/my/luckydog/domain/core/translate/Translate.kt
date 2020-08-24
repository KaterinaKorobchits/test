package my.luckydog.domain.core.translate

sealed class Translate {

    data class Success(val text: String) : Translate()

    object TextSizeExceeded : Translate()
    object Error : Translate()
}