package my.luckydog.data.core.translate

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApi {

    @GET("v1.5/tr.json/translate")
    fun getTranslate(
        @Query("text") text: String,
        @Query("lang") lang: String
    ): Single<TranslatedText>

    @GET("v1.5/tr.json/detect")
    fun detectLang(
        @Query("text") text: String,
        @Query("hint") lang: String
    ): Single<DetectedLang>

    @GET("v1.5/tr.json/translate")
    suspend fun getTranslateS(
        @Query("text") text: String,
        @Query("lang") lang: String
    ): TranslatedText

    @GET("v1.5/tr.json/detect")
    suspend fun detectLangS(
        @Query("text") text: String,
        @Query("hint") lang: String
    ): DetectedLang
}