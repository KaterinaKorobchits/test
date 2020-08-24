package my.luckydog.domain.notes

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.notes.Note
import my.luckydog.boundaries.notes.NotesRepository
import my.luckydog.common.Mapper
import my.luckydog.interactors.notes.ItemNote
import javax.inject.Inject

class UpdateCaseImpl @Inject constructor(
    private val repository: NotesRepository,
    private val mapper: Mapper<ItemNote, Note>
) : UpdateCase {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun update(notes: List<ItemNote>): Completable {
        return repository.update(notes.map { mapper.transform(it) })
            .subscribeOn(Schedulers.io())
    }
}