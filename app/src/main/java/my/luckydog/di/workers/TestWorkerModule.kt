package my.luckydog.di.workers

import dagger.Binds
import dagger.Module
import my.luckydog.boundaries.app.config.RemoteConfig
import my.luckydog.data.app.config.RemoteConfigImpl
import my.luckydog.domain.workers.TestWorkerInteractorImpl
import my.luckydog.interactors.workers.TestWorkerInteractor

@Module
abstract class TestWorkerModule {

    @Binds
    abstract fun provideRepository(repository: RemoteConfigImpl): RemoteConfig

    @Binds
    abstract fun provideInteractor(interactor: TestWorkerInteractorImpl): TestWorkerInteractor
}