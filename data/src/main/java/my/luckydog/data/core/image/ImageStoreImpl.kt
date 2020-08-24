package my.luckydog.data.core.image

import my.luckydog.boundaries.core.image.Image
import my.luckydog.boundaries.core.image.ImageStore
import my.luckydog.boundaries.core.image.ResultImage
import java.util.*
import javax.inject.Inject

class ImageStoreImpl @Inject constructor() : ImageStore {

    private val images = ArrayList<Image>()
    private var loadedPages = 0
    private var totalPages = 0

    override fun store(result: ResultImage) {
        images.addAll(result.photo)
        loadedPages = result.page
        totalPages = result.pages
    }

    override fun getImages(): List<Image> = images

    override fun getTotalPages(): Int = totalPages

    override fun getLoadedPages(): Int = loadedPages

    override fun clear() {
        images.clear()
        loadedPages = 0
    }
}