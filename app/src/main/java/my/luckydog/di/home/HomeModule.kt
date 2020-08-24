package my.luckydog.di.home

import dagger.Binds
import dagger.Module
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.domain.home.HomeInteractorImpl
import my.luckydog.interactors.HomeInteractor
import my.luckydog.navigation.HomeNavigationImpl
import my.luckydog.presentation.fragments.home.navigation.HomeNavigation

@Module
abstract class HomeModule {

    @Binds
    @FragmentScope
    abstract fun provideNavigation(navigation: HomeNavigationImpl): HomeNavigation

    @Binds
    @FragmentScope
    abstract fun provideInteractor(interactor: HomeInteractorImpl): HomeInteractor
}