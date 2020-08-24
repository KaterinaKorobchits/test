package my.luckydog.data.core.dictionary.entities

import com.google.gson.annotations.SerializedName

data class ResponseDicEntry(
    @SerializedName("text") val text: String,
    @SerializedName("pos") val pos:  String?,
    @SerializedName("ts") val transcription: String,
    @SerializedName("tr") val translations: List<ResponseTranslation>?
)