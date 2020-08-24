package my.luckydog.domain.core.image

import my.luckydog.boundaries.core.image.Image
import my.luckydog.common.Mapper
import my.luckydog.interactors.core.image.ItemImage
import javax.inject.Inject

class ItemImageMapper @Inject constructor() : Mapper<Image, ItemImage> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: Image) = ItemImage(entity.id, entity.url)
}