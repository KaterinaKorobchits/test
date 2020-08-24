package my.luckydog.domain.core.translate

import io.reactivex.Single

interface DetectCase {

    fun detect(text: String, hint: List<String>): Single<Detect>

    suspend fun detectS(text: String, hint: List<String>): Detect
}