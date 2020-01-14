package my.luckydog.di.splash

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.domain.splash.SplashInteractorImpl
import my.luckydog.interactors.SplashInteractor
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.navigation.SplashNavigationImpl
import my.luckydog.presentation.fragments.splash.navigation.SplashNavigation

@Module
class SplashModule {

    @Provides
    @SplashScope
    fun provideNavigation(producer: NavigatorProducer): SplashNavigation =
        SplashNavigationImpl(producer)

    @Provides
    @SplashScope
    fun provideInteractor(
        authStore: AuthStore,
        sessionStore: SessionStore
    ): SplashInteractor = SplashInteractorImpl(sessionStore, authStore)
}