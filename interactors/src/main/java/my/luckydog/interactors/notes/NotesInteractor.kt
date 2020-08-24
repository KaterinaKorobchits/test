package my.luckydog.interactors.notes

import io.reactivex.Completable
import io.reactivex.Single

interface NotesInteractor {

    fun add(word: String): Single<SideEffect>

    fun delete(note: ItemNote): Completable

    fun update(notes: List<ItemNote>): Completable

    fun getAll(): Single<SideEffect>

    fun translate(word: String): Single<SideEffect>

    suspend fun translateS(word: String): SideEffect
}