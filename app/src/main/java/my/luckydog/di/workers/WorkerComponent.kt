package my.luckydog.di.workers

import dagger.Component
import my.luckydog.presentation.workers.TestWorker

@Component(modules = [TestWorkerModule::class])
interface WorkerComponent {

    fun inject(worker: TestWorker)
}