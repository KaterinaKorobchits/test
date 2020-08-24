package my.luckydog.domain.notes

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.luckydog.common.withTimeoutOrNullDefaultContext
import my.luckydog.domain.core.translate.Detect
import my.luckydog.domain.core.translate.DetectCase
import my.luckydog.domain.core.translate.Translate
import my.luckydog.domain.core.translate.TranslateCase
import my.luckydog.interactors.notes.ItemNote
import my.luckydog.interactors.notes.NotesInteractor
import my.luckydog.interactors.notes.SideEffect
import my.luckydog.interactors.notes.SideEffect.Dialog.ShowDetectLangFail
import my.luckydog.interactors.notes.SideEffect.Dialog.ShowUnknownError
import my.luckydog.interactors.notes.SideEffect.UpdateUi.*
import javax.inject.Inject

class NotesInteractorImpl @Inject constructor(
    private val add: AddCase,
    private val delete: DeleteCase,
    private val update: UpdateCase,
    private val getAll: GetAllCase,
    private val detect: DetectCase,
    private val translate: TranslateCase
) : NotesInteractor {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun add(word: String) = add.add(word)
        .map { AddNote(ItemNote(it, word, it)) }
        .cast(SideEffect::class.java)

    override fun delete(note: ItemNote) = delete.delete(note)

    override fun update(notes: List<ItemNote>) = update.update(notes)

    override fun getAll() = getAll.getAll()
        .map { ShowAllNotes(it) }
        .cast(SideEffect::class.java)

    override fun translate(word: String) = detect.detect(word, langs)
        .flatMap { detect ->
            if (detect is Detect.Success) {
                val langFrom = detect.lang
                translate.translate(word, langFrom, langs.filterNot { it == langFrom }.first())
                    .map { tr ->
                        if (tr is Translate.Success) ShowTranslate(tr.text) else ShowDetectLangFail
                    }
                    .cast(SideEffect::class.java)
            } else Single.just(ShowDetectLangFail)
        }
        .subscribeOn(Schedulers.io())
        .onErrorReturn { ShowUnknownError }

    override suspend fun translateS(word: String) = withTimeoutOrNullDefaultContext(10) {
        val detect = detect.detectS(word, langs)
        if (detect is Detect.Success) {
            val langFrom = detect.lang
            translate.translateS(word, langFrom, langs.filterNot { it == langFrom }.first()).let {
                if (it is Translate.Success) ShowTranslate(it.text) else ShowDetectLangFail
            }
        } else ShowDetectLangFail
    } ?: ShowDetectLangFail
}