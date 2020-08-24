package my.luckydog.interactors

import io.reactivex.Single
import my.luckydog.interactors.core.image.ImageEffect

interface HomeInteractor {

    fun storeHasAuthorize()

    fun logout()

    fun images(text: String): Single<ImageEffect>
}