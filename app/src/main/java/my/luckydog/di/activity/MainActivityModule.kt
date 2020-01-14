package my.luckydog.di.activity

import dagger.Module
import dagger.Provides
import my.luckydog.activity.MainInjector
import my.luckydog.activity.MainInjectorImpl

@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    fun provideInjector(): MainInjector = MainInjectorImpl()
}