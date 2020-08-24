package my.luckydog.domain.core.translate

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import my.luckydog.boundaries.core.translate.TranslateRepository
import my.luckydog.boundaries.core.translate.errors.TranslateError.ExceededTextSize
import my.luckydog.boundaries.core.translate.errors.TranslateErrorHandler
import my.luckydog.common.Result
import my.luckydog.common.asyncIO
import my.luckydog.domain.core.translate.Translate.*
import javax.inject.Inject

class TranslateCaseImpl @Inject constructor(
    private val repository: TranslateRepository,
    private val errors: TranslateErrorHandler
) : TranslateCase {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun translate(word: String, langFrom: String, langTo: String): Single<Translate> {
        return repository.translate(word, langFrom, langTo)
            .map { Success(it.joinToString()) }
            .cast(Translate::class.java)
            .subscribeOn(Schedulers.io())
            .onErrorReturn { if (errors.process(it) is ExceededTextSize) TextSizeExceeded else Error }
    }

    override suspend fun translateS(word: String, langFrom: String, langTo: String): Translate =
        withContext(newSingleThreadContext("Abcdef")) {
            val translate1 = asyncIO { repository.translateS(word, langFrom, langTo) }
            val translate2 = asyncIO { repository.translateS("Wonderful", langFrom, langTo) }

            doSome(translate1.await(), translate2.await())
        }

    private fun doSome(
        translate1: Result<List<String>>,
        translate2: Result<List<String>>
    ): Translate = when (translate1) {
        is Result.Success -> Success(translate1.data.joinToString())
        is Result.Error -> {
            if (errors.process(translate1.throwable) is ExceededTextSize) TextSizeExceeded else Error
        }
    }
}