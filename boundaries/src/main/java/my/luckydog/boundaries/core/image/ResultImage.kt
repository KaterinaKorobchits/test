package my.luckydog.boundaries.core.image

data class ResultImage(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: List<Image>
)