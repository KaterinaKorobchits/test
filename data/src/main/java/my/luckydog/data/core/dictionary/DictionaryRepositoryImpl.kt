package my.luckydog.data.core.dictionary

import io.reactivex.Single
import my.luckydog.boundaries.core.dictionary.DictionaryRepository
import my.luckydog.boundaries.core.dictionary.entities.DicEntry
import my.luckydog.common.Mapper
import my.luckydog.data.core.dictionary.entities.ResponseDicEntry
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val mapper: Mapper<ResponseDicEntry, DicEntry>
) : DictionaryRepository {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun lookup(text: String, langFrom: String, langTo: String): Single<List<DicEntry>> {
        return api.lookup( text, "$langFrom-$langTo")
            .map { mapper.transform(it.def) }
    }
}