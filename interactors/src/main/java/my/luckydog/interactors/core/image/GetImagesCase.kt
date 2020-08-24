package my.luckydog.interactors.core.image

import io.reactivex.Single

interface GetImagesCase {

    fun nextImages(text: String): Single<ImageEffect>
}