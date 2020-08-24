package my.luckydog.di.signup

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.signup.errors.SignUpErrorHandler
import my.luckydog.boundaries.signup.repositories.SignUpRepository
import my.luckydog.data.signup.SignUpErrorHandlerImpl
import my.luckydog.data.signup.SignUpRepositoryImpl
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.domain.signup.SignUpCase
import my.luckydog.domain.signup.SignUpCaseImpl
import my.luckydog.domain.signup.SignUpInteractorImpl
import my.luckydog.interactors.signup.SignUpInteractor
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.navigation.SignUpNavigationImpl
import my.luckydog.presentation.fragments.signup.dialogs.SignUpDialogs
import my.luckydog.presentation.fragments.signup.dialogs.SignUpDialogsImpl
import my.luckydog.presentation.fragments.signup.navigation.SignUpNavigation
import my.luckydog.presentation.fragments.signup.views.AppSettingsView
import my.luckydog.presentation.fragments.signup.views.AppSettingsViewImpl
import my.luckydog.presentation.fragments.signup.views.BrowserView
import my.luckydog.presentation.fragments.signup.views.BrowserViewImpl
import javax.inject.Named

@Module
abstract class SignUpModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @FragmentScope
        fun provideNavigation(
            @Named(APP_CONTEXT)
            context: Context,
            producer: NavigatorProducer,
            browserView: BrowserView,
            appSettingsView: AppSettingsView
        ): SignUpNavigation = SignUpNavigationImpl(context, producer, browserView, appSettingsView)
    }

    @Binds
    @FragmentScope
    abstract fun provideDialogs(dialogs: SignUpDialogsImpl): SignUpDialogs

    @Binds
    @FragmentScope
    abstract fun provideInteractor(interactor: SignUpInteractorImpl): SignUpInteractor

    @Binds
    @FragmentScope
    abstract fun provideSignUpCase(case: SignUpCaseImpl): SignUpCase

    @Binds
    @FragmentScope
    abstract fun provideRepository(repository: SignUpRepositoryImpl): SignUpRepository

    @Binds
    @FragmentScope
    abstract fun provideErrorHandler(errors: SignUpErrorHandlerImpl): SignUpErrorHandler

    @Binds
    @FragmentScope
    abstract fun provideBrowserView(view: BrowserViewImpl): BrowserView

    @Binds
    @FragmentScope
    abstract fun provideAppSettingsView(view: AppSettingsViewImpl): AppSettingsView
}