package my.luckydog.data.core.dictionary.mappers

import my.luckydog.boundaries.core.dictionary.entities.Example
import my.luckydog.common.Mapper
import my.luckydog.data.core.dictionary.entities.ResponseExample
import javax.inject.Inject

class ExampleMapper @Inject constructor() : Mapper<ResponseExample, Example> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: ResponseExample): Example {
        return Example(entity.text, entity.translation.map { it.text })
    }
}