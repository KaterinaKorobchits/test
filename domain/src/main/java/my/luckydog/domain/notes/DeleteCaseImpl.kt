package my.luckydog.domain.notes

import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.notes.Note
import my.luckydog.boundaries.notes.NotesRepository
import my.luckydog.common.Mapper
import my.luckydog.interactors.notes.ItemNote
import javax.inject.Inject

class DeleteCaseImpl @Inject constructor(
    private val repository: NotesRepository,
    private val mapper: Mapper<ItemNote, Note>
) : DeleteCase {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun delete(note: ItemNote) = repository.delete(mapper.transform(note))
        .subscribeOn(Schedulers.io())
}