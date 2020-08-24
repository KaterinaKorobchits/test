package my.luckydog.di.activity

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.activity.MainInjector
import my.luckydog.activity.MainInjectorImpl
import my.luckydog.di.scopes.ActivityScope
import my.luckydog.drawer.DrawerBinder
import my.luckydog.drawer.DrawerManagerImpl
import my.luckydog.presentation.core.DrawerManager
import my.luckydog.presentation.core.theme.ThemeRepository
import my.luckydog.presentation.core.theme.ThemeRepositoryImpl

@Module
abstract class MainActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @ActivityScope
        fun provideDrawerManagerImpl(): DrawerManagerImpl {
            println("!!!dagger: provideDrawerManagerImpl")
            return DrawerManagerImpl()
        }
    }

    @Binds
    @ActivityScope
    abstract fun provideInjector(injector: MainInjectorImpl): MainInjector

    @Binds
    @ActivityScope
    abstract fun provideDrawerBinder(drawerManager: DrawerManagerImpl): DrawerBinder

    @Binds
    @ActivityScope
    abstract fun provideDrawerManager(drawerManager: DrawerManagerImpl): DrawerManager

    @Binds
    abstract fun provideThemeRepository(themeRepository: ThemeRepositoryImpl): ThemeRepository
}