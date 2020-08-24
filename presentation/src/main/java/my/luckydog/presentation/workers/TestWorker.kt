package my.luckydog.presentation.workers

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import my.luckydog.common.HasWorkerInjector
import my.luckydog.interactors.workers.TestWorkerInteractor
import javax.inject.Inject

class TestWorker(context: Context, params: WorkerParameters) : RxWorker(context, params) {

    init {
        (applicationContext as HasWorkerInjector).getWorkerInjector().inject(this)
    }

    @Inject
    lateinit var remote: TestWorkerInteractor

    override fun createWork(): Single<Result> {
        return remote.getTestValue()
            .map { println("createWork - map $it"); Result.success() }
            .onErrorReturn { println("createWork - onErrorReturn $it}"); Result.failure() }
    }
}

