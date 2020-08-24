package my.luckydog.data.core.image

import my.luckydog.boundaries.core.image.Image
import my.luckydog.boundaries.core.image.ResultImage
import my.luckydog.common.Mapper
import my.luckydog.data.core.image.entities.ResponseImages
import my.luckydog.data.core.image.entities.ResponsePhoto
import javax.inject.Inject

class ResultImageMapper @Inject constructor() : Mapper<ResponseImages, ResultImage> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: ResponseImages) = with(entity.photos) {
        ResultImage(page, pages, perpage, total, photo.map { Image(it.id, getUrl(it)) })
    }

    private fun getUrl(response: ResponsePhoto): String = with(response) {
        when {
            urlC != null -> urlC
            urlZ != null -> urlZ
            urlN != null -> urlN
            urlM != null -> urlM
            urlQ != null -> urlQ
            urlS != null -> urlS
            else -> ""
        }
    }
}