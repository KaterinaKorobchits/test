package my.luckydog.domain.core.translate

sealed class Detect {

    data class Success(val lang: String) : Detect()

    object Error : Detect()
}