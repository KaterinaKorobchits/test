package my.luckydog.di.signin

import dagger.Binds
import dagger.Module
import my.luckydog.boundaries.signin.errors.SignInErrorHandler
import my.luckydog.boundaries.signin.repositories.SignInRepository
import my.luckydog.data.signin.SignInErrorHandlerImpl
import my.luckydog.data.signin.SignInRepositoryImpl
import my.luckydog.di.scopes.FragmentScope
import my.luckydog.domain.signin.SignInCase
import my.luckydog.domain.signin.SignInCaseImpl
import my.luckydog.domain.signin.SignInInteractorImpl
import my.luckydog.interactors.signin.SignInInteractor
import my.luckydog.navigation.SignInNavigationImpl
import my.luckydog.presentation.fragments.signin.dialogs.SignInDialogs
import my.luckydog.presentation.fragments.signin.dialogs.SignInDialogsImpl
import my.luckydog.presentation.fragments.signin.navigation.SignInNavigation

@Module
abstract class SignInModule {

    @Binds
    @FragmentScope
    abstract fun provideNavigation(navigation: SignInNavigationImpl): SignInNavigation

    @Binds
    @FragmentScope
    abstract fun provideDialogs(dialogs: SignInDialogsImpl): SignInDialogs

    @Binds
    @FragmentScope
    abstract fun provideInteractor(interactor: SignInInteractorImpl): SignInInteractor

    @Binds
    @FragmentScope
    abstract fun provideSignInCase(case: SignInCaseImpl): SignInCase

    @Binds
    @FragmentScope
    abstract fun provideRepository(repository: SignInRepositoryImpl): SignInRepository

    @Binds
    @FragmentScope
    abstract fun provideErrorHandler(errors: SignInErrorHandlerImpl): SignInErrorHandler
}