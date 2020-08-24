package my.luckydog.data.core.dictionary.mappers

import my.luckydog.boundaries.core.dictionary.entities.Example
import my.luckydog.boundaries.core.dictionary.entities.Translation
import my.luckydog.common.Mapper
import my.luckydog.data.core.dictionary.entities.ResponseExample
import my.luckydog.data.core.dictionary.entities.ResponseTranslation
import javax.inject.Inject

class TranslationMapper @Inject constructor(
    private val mapper: Mapper<ResponseExample, Example>
) : Mapper<ResponseTranslation, Translation> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: ResponseTranslation): Translation {
        val examples = entity.examples?.let { mapper.transform(it) } ?: emptyList()
        return Translation(entity.text, examples)
    }
}