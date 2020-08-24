package my.luckydog.data.core.image.entities

import com.google.gson.annotations.SerializedName

data class ResponseImages(
    @SerializedName("stat") val stat: String,
    @SerializedName("photos") val photos: ResponsePhotos,
    @SerializedName("code") val code: Int?
)