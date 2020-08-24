package my.luckydog.data.core.translate

import com.google.gson.annotations.SerializedName

data class DetectedLang(
    @SerializedName("code") val code: String,
    @SerializedName("lang") val lang: String
)