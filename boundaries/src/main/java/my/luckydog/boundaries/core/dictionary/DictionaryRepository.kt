package my.luckydog.boundaries.core.dictionary

import io.reactivex.Single
import my.luckydog.boundaries.core.dictionary.entities.DicEntry

interface DictionaryRepository {

    fun lookup(text: String, langFrom: String, langTo: String): Single<List<DicEntry>>
}