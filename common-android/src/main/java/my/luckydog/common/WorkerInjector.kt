package my.luckydog.common

import androidx.work.ListenableWorker

interface WorkerInjector {

    fun inject(worker: ListenableWorker)
}