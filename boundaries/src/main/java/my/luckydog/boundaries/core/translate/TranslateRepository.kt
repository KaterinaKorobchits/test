package my.luckydog.boundaries.core.translate

import io.reactivex.Single
import my.luckydog.common.Result

interface TranslateRepository {

    fun translate(text: String, langFrom: String, langTo: String): Single<List<String>>

    fun detect(text: String, hint: List<String>): Single<String>

    suspend fun translateS(text: String, langFrom: String, langTo: String): Result<List<String>>

    suspend fun detectS(text: String, hint: List<String>): Result<String>
}