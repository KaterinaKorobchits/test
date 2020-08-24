package my.luckydog.data.core.dictionary.entities

import com.google.gson.annotations.SerializedName

data class DicResult(
    @SerializedName("def") val def: List<ResponseDicEntry>
)