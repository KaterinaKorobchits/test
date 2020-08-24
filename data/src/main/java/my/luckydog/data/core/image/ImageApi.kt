package my.luckydog.data.core.image

import io.reactivex.Single
import my.luckydog.data.core.image.entities.ResponseImages
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("rest/")
    fun getImages(
        @Query("text") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ResponseImages>
}