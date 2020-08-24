package my.luckydog.di.workers

import androidx.work.ListenableWorker
import my.luckydog.common.WorkerInjector
import my.luckydog.presentation.workers.TestWorker

class WorkerInjectorImpl : WorkerInjector {

    override fun inject(worker: ListenableWorker) {
        if (worker is TestWorker)
            DaggerWorkerComponent.builder()
                .build()
                .inject(worker)
    }
}