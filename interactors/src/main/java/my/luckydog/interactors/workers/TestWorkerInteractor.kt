package my.luckydog.interactors.workers

import io.reactivex.Single

interface TestWorkerInteractor {

    fun getTestValue(): Single<String>
}