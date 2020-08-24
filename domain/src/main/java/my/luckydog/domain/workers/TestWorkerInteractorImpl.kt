package my.luckydog.domain.workers

import my.luckydog.boundaries.app.config.RemoteConfig
import my.luckydog.interactors.workers.TestWorkerInteractor
import javax.inject.Inject

class TestWorkerInteractorImpl @Inject constructor(
    private val config: RemoteConfig
) : TestWorkerInteractor {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun getTestValue() = config.configure()
        .andThen(config.getString(TEST_VALUE))
}