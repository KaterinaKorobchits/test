package my.luckydog.domain.notes

import io.reactivex.Completable
import my.luckydog.interactors.notes.ItemNote

interface DeleteCase {

    fun delete(note: ItemNote): Completable
}