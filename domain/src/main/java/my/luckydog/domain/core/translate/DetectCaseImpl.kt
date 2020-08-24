package my.luckydog.domain.core.translate

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.core.translate.TranslateRepository
import my.luckydog.common.Result
import my.luckydog.domain.core.translate.Detect.Error
import my.luckydog.domain.core.translate.Detect.Success
import javax.inject.Inject

class DetectCaseImpl @Inject constructor(private val repository: TranslateRepository) : DetectCase {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun detect(text: String, hint: List<String>): Single<Detect> {
        return repository.detect(text, hint)
            .map { Success(it) }
            .cast(Detect::class.java)
            .subscribeOn(Schedulers.io())
            .onErrorReturn { Error }
    }

    override suspend fun detectS(text: String, hint: List<String>): Detect {
        val detect = repository.detectS(text, hint)
        return if (detect is Result.Success) Success(detect.data) else Error
    }
}