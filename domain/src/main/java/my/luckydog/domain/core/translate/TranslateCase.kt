package my.luckydog.domain.core.translate

import io.reactivex.Single

interface TranslateCase {

    fun translate(word: String, langFrom: String, langTo: String): Single<Translate>

    suspend fun translateS(word: String, langFrom: String, langTo: String): Translate
}