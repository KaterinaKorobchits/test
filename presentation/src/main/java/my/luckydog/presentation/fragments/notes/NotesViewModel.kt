package my.luckydog.presentation.fragments.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import my.luckydog.common.extensions.move
import my.luckydog.interactors.notes.NotesInteractor
import my.luckydog.interactors.notes.SideEffect
import my.luckydog.interactors.notes.SideEffect.Dialog
import my.luckydog.interactors.notes.SideEffect.Dialog.ShowUnknownError
import my.luckydog.interactors.notes.SideEffect.UpdateUi
import my.luckydog.interactors.notes.SideEffect.UpdateUi.*
import my.luckydog.presentation.core.Event
import my.luckydog.presentation.fragments.notes.dialogs.NotesDialogs
import kotlin.LazyThreadSafetyMode.NONE

class NotesViewModel(
    private val interactor: NotesInteractor,
    val form: NotesForm,
    val dialogs: NotesDialogs
) : ViewModel() {

    private val composite by lazy(NONE) { CompositeDisposable() }

    val showTranslate: LiveData<Event<String>>
        get() = translate
    private val translate = MutableLiveData<Event<String>>()

    val loading: LiveData<Event<Boolean>>
        get() = load
    private val load = MutableLiveData<Event<Boolean>>()

    val showDialog: LiveData<Event<Dialog>>
        get() = dialog
    private val dialog = MutableLiveData<Event<Dialog>>()

    val addNote: (text: String) -> Unit = { addNote(it) }
    private fun addNote(word: String) {
        val addNote = interactor.add(word)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ process(it) }, { process(ShowUnknownError) })
        composite.add(addNote)
    }

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }

    fun delete(notePosition: Int) {
        viewModelScope
        val toDelete = form.notes[notePosition]
        form.notes.removeAt(notePosition)
        val deleteNote = interactor.delete(toDelete)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ println("Note deleted") }, { process(ShowUnknownError) })
        composite.add(deleteNote)
    }

    fun moveNote(fromPosition: Int, toPosition: Int) {
        form.notes.move(fromPosition, toPosition)
        val updateNotes = interactor.update(form.notes)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ println("Note updates") }, { process(ShowUnknownError) })
        composite.add(updateNotes)
    }

    fun getAll() {
        val getAll = interactor.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { load.value = Event(true) }
            .subscribe({ process(it) }, { process(ShowUnknownError) })
        composite.add(getAll)
    }

    fun clickNote(word: String) {
        println("click note")
        viewModelScope.launch {
            process(interactor.translateS(word))
        }
        /*val clickNote = interactor.translate(word)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ process(it) }, { process(ShowUnknownError) })
        composite.add(clickNote)*/

    }

    private fun process(effect: SideEffect) = when (effect) {
        is UpdateUi -> update(effect)
        is Dialog -> dialog.value = Event(effect)
    }

    private fun update(update: UpdateUi) {
        when (update) {
            is ShowTranslate -> translate.value = Event(update.text)
            is ShowAllNotes -> form.notes.apply { clear(); addAll(update.notes);println("Note getall ${update.notes}") }
            is AddNote -> {
                form.notes.add(update.note);println("Note added ${update.note}")
            }
        }
    }
}