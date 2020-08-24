package my.luckydog.data.core.translate

import com.google.gson.annotations.SerializedName

data class TranslatedText(
    @SerializedName("code") val code: String,
    @SerializedName("lang") val lang: String,
    @SerializedName("text") val text: List<String>
)