package my.luckydog.data.core.image

import io.reactivex.Single
import my.luckydog.boundaries.core.image.ImageRepository
import my.luckydog.boundaries.core.image.ResultImage
import my.luckydog.common.Mapper
import my.luckydog.data.core.image.entities.ResponseImages
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: ImageApi,
    private val mapper: Mapper<ResponseImages, ResultImage>
) : ImageRepository {

    override fun getImages(text: String, page: Int, perpage: Int): Single<ResultImage> {
        return api.getImages(text, page, perpage)
            .map { mapper.transform(it) }
    }
}