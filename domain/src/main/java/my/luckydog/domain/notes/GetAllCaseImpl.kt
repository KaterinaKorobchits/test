package my.luckydog.domain.notes

import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.boundaries.notes.Note
import my.luckydog.boundaries.notes.NotesRepository
import my.luckydog.common.Mapper
import my.luckydog.interactors.notes.ItemNote
import javax.inject.Inject

class GetAllCaseImpl @Inject constructor(
    private val repository: NotesRepository,
    private val session: SessionStore,
    private val mapper: Mapper<Note, ItemNote>
) : GetAllCase {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun getAll() = repository.getAll(session.getUserId())
        .map { mapper.transform(it) }
        .subscribeOn(Schedulers.io())
}