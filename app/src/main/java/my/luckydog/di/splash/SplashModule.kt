package my.luckydog.di.splash

import dagger.Binds
import dagger.Module
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.domain.splash.SplashInteractorImpl
import my.luckydog.interactors.SplashInteractor
import my.luckydog.navigation.SplashNavigationImpl
import my.luckydog.presentation.fragments.splash.navigation.SplashNavigation

@Module
abstract class SplashModule {

    @Binds
    @FragmentScope
    abstract fun provideNavigation(navigation: SplashNavigationImpl): SplashNavigation

    @Binds
    @FragmentScope
    abstract fun provideInteractor(interactor: SplashInteractorImpl): SplashInteractor
}