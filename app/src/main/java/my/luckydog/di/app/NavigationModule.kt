package my.luckydog.di.app

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.navigation.Navigator
import my.luckydog.navigation.NavigatorBinder
import my.luckydog.navigation.NavigatorProducer
import javax.inject.Singleton

@Module
abstract class NavigationModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideNavigator(): Navigator = Navigator()
    }

    @Binds
    @Singleton
    abstract fun provideNavigatorBinder(navigator: Navigator): NavigatorBinder

    @Binds
    @Singleton
    abstract fun provideNavigatorProvider(navigator: Navigator): NavigatorProducer
}