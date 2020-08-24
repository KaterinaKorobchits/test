package my.luckydog.data.core.translate

import io.reactivex.Single
import io.reactivex.Single.error
import io.reactivex.Single.just
import my.luckydog.boundaries.core.translate.TranslateRepository
import my.luckydog.boundaries.core.translate.errors.DetectFail
import my.luckydog.boundaries.core.translate.errors.TranslateFail
import my.luckydog.common.Result
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor(
    private val api: TranslateApi,
    private val cache: TranslateCache
) : TranslateRepository {

    private companion object {
        private const val CODE_OK = "200"
    }

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun translate(text: String, langFrom: String, langTo: String): Single<List<String>> {
        return cache.get(text)?.let { just(it) } ?: api.getTranslate(text, "$langFrom-$langTo")
            .flatMap {
                if (it.code != CODE_OK) error(TranslateFail(it.code)) else just(it.text)
                    .doOnSuccess { list -> cache.put(text, list) }
            }
    }

    override fun detect(text: String, hint: List<String>): Single<String> {
        return api.detectLang(text, hint.joinToString(separator = ","))
            .flatMap { if (it.code != CODE_OK) error(DetectFail()) else just(it.lang) }
    }

    override suspend fun translateS(
        text: String,
        langFrom: String,
        langTo: String
    ): Result<List<String>> {
        return cache.get(text)?.let { Result.Success(it) } ?: try {
            println("!!!!!! ${Thread.currentThread()}")
            val translate = api.getTranslateS(text, "$langFrom-$langTo")
            if (translate.code != CODE_OK) Result.Error(TranslateFail(translate.code)) else
                Result.Success(translate.text)
        } catch (throwable: Throwable) {
            Result.Error(TranslateFail())
        }
    }

    override suspend fun detectS(text: String, hint: List<String>): Result<String> {
        return try {
            val detect = api.detectLangS(text, hint.joinToString(separator = ","))
            if (detect.code != CODE_OK) throw DetectFail() else Result.Success(detect.lang)
        } catch (throwable: Throwable) {
            Result.Error(DetectFail())
        }
    }
}