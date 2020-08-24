package my.luckydog.data.core.image.entities

import com.google.gson.annotations.SerializedName

data class ResponsePhoto(
    @SerializedName("id") val id: String,
    @SerializedName("url_o") val urlO: String?,
    @SerializedName("url_s") val urlS: String?,
    @SerializedName("url_q") val urlQ: String?,
    @SerializedName("url_m") val urlM: String?,
    @SerializedName("url_n") val urlN: String?,
    @SerializedName("url_z") val urlZ: String?,
    @SerializedName("url_c") val urlC: String?
)