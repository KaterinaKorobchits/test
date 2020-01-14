package my.luckydog.di.home

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.domain.home.HomeInteractorImpl
import my.luckydog.interactors.HomeInteractor
import my.luckydog.navigation.HomeNavigationImpl
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.presentation.fragments.home.navigation.HomeNavigation

@Module
class HomeModule {

    @Provides
    @HomeScope
    fun provideNavigation(producer: NavigatorProducer): HomeNavigation =
        HomeNavigationImpl(producer)

    @Provides
    @HomeScope
    fun provideInteractor(
        authStore: AuthStore,
        sessionStore: SessionStore
    ): HomeInteractor = HomeInteractorImpl(sessionStore, authStore)
}