package my.luckydog.domain.core.image

import io.reactivex.Single
import my.luckydog.boundaries.core.image.Image
import my.luckydog.boundaries.core.image.ImageRepository
import my.luckydog.boundaries.core.image.ImageStore
import my.luckydog.common.Mapper
import my.luckydog.interactors.core.image.GetImagesCase
import my.luckydog.interactors.core.image.ImageEffect
import my.luckydog.interactors.core.image.ItemImage
import javax.inject.Inject

class GetImagesCaseImpl @Inject constructor(
    private val repository: ImageRepository,
    private val store: ImageStore,
    private val mapper: Mapper<Image, ItemImage>
) : GetImagesCase {

    override fun nextImages(text: String): Single<ImageEffect> {
        return if (store.getLoadedPages() != 0 && store.getLoadedPages() + 1 > store.getTotalPages()) Single.just(
            ImageEffect.NoMore) else
            repository.getImages(text, store.getLoadedPages() + 1, 10)
                .map {store.store(it)
                    ImageEffect.Success(mapper.transform(it.photo)) }
                .cast(ImageEffect::class.java)
                .onErrorReturn { ImageEffect.Fail }
    }
}