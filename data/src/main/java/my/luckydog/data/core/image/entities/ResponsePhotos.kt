package my.luckydog.data.core.image.entities

import com.google.gson.annotations.SerializedName

data class ResponsePhotos(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perpage: Int,
    @SerializedName("total") val total: String,
    @SerializedName("photo") val photo: List<ResponsePhoto>
)