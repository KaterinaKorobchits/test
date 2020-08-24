package my.luckydog.data.core.dictionary.mappers

import my.luckydog.boundaries.core.dictionary.entities.DicEntry
import my.luckydog.boundaries.core.dictionary.entities.Translation
import my.luckydog.common.Mapper
import my.luckydog.data.core.dictionary.entities.ResponseDicEntry
import my.luckydog.data.core.dictionary.entities.ResponseTranslation
import javax.inject.Inject

class DicEntryMapper @Inject constructor(
    private val mapper: Mapper<ResponseTranslation, Translation>
) : Mapper<ResponseDicEntry, DicEntry> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: ResponseDicEntry): DicEntry {
        return with(entity) {
            if (pos == null || translations == null) DicEntry.Fail else
                DicEntry.Success(text, pos, transcription, mapper.transform(translations))
        }
    }
}