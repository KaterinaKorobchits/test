package my.luckydog.data.core.dictionary

import io.reactivex.Single
import my.luckydog.data.core.dictionary.entities.DicResult
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("v1/dicservice.json/lookup")
    fun lookup(
        @Query("text") text: String,
        @Query("lang") lang: String
    ): Single<DicResult>
}