package my.luckydog.domain.notes

import io.reactivex.Completable
import my.luckydog.interactors.notes.ItemNote

interface UpdateCase {

    fun update(notes: List<ItemNote>): Completable
}