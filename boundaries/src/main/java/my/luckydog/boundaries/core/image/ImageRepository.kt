package my.luckydog.boundaries.core.image

import io.reactivex.Single

interface ImageRepository {

    fun getImages(text: String, page: Int, perpage: Int): Single<ResultImage>
}