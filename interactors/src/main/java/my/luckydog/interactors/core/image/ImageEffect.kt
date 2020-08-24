package my.luckydog.interactors.core.image

sealed class ImageEffect {

    data class Success(val items: List<ItemImage>) : ImageEffect()

    object NoMore : ImageEffect()
    object Fail : ImageEffect()
}