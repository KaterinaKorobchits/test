package my.luckydog.domain.notes

import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.boundaries.notes.NotesRepository
import javax.inject.Inject

class AddCaseImpl @Inject constructor(
    private val repository: NotesRepository,
    private val session: SessionStore
) : AddCase {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun add(word: String) = repository.add(word, session.getUserId())
        .subscribeOn(Schedulers.io())
}