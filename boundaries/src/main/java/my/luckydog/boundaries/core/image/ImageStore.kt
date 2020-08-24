package my.luckydog.boundaries.core.image

interface ImageStore {

    fun store(result: ResultImage)

    fun getImages(): List<Image>

    fun getTotalPages(): Int

    fun getLoadedPages(): Int

    fun clear()
}