package my.luckydog.boundaries.notes

import io.reactivex.Completable
import io.reactivex.Single

interface NotesRepository {

    fun add(word: String, userId: Long): Single<Long>

    fun delete(note: Note): Completable

    fun update(notes: List<Note>): Completable

    fun getAll(userId: Long): Single<List<Note>>
}