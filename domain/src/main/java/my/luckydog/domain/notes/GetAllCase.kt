package my.luckydog.domain.notes

import io.reactivex.Single
import my.luckydog.interactors.notes.ItemNote

interface GetAllCase {

    fun getAll(): Single<List<ItemNote>>
}