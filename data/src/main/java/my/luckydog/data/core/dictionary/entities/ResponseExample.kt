package my.luckydog.data.core.dictionary.entities

import com.google.gson.annotations.SerializedName

data class ResponseExample(
    @SerializedName("text") val text: String,
    @SerializedName("tr") val translation: List<ResponseTrExample>
)