package my.luckydog.domain.notes

import io.reactivex.Single

interface AddCase {

    fun add(word: String): Single<Long>
}