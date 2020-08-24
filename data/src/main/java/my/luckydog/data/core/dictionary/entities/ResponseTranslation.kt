package my.luckydog.data.core.dictionary.entities

import com.google.gson.annotations.SerializedName

data class ResponseTranslation(
    @SerializedName("text") val text: String,
    @SerializedName("pos") val pos: String?,
    @SerializedName("ex") val examples: List<ResponseExample>?
)