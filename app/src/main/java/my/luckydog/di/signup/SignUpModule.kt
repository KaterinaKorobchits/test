package my.luckydog.di.signup

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.boundaries.signup.errors.SignUpErrorHandler
import my.luckydog.boundaries.signup.repositories.SignUpRepository
import my.luckydog.data.bd.LuckyDogDatabase
import my.luckydog.data.bd.UserDao
import my.luckydog.data.signup.SignUpErrorHandlerImpl
import my.luckydog.data.signup.SignUpRepositoryImpl
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.domain.signup.SignUpCase
import my.luckydog.domain.signup.SignUpCaseImpl
import my.luckydog.domain.signup.SignUpInteractorImpl
import my.luckydog.domain.validators.EmailCase
import my.luckydog.domain.validators.EmailCaseImpl
import my.luckydog.domain.validators.PasswordCase
import my.luckydog.domain.validators.PasswordCaseImpl
import my.luckydog.interactors.signup.SignUpInteractor
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.navigation.SignUpNavigationImpl
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.fragments.signup.dialogs.SignUpDialogs
import my.luckydog.presentation.fragments.signup.dialogs.SignUpDialogsImpl
import my.luckydog.presentation.fragments.signup.navigation.SignUpNavigation
import my.luckydog.presentation.utils.BrowserView
import my.luckydog.presentation.utils.BrowserViewImpl
import javax.inject.Named

@Module
class SignUpModule {

    @Provides
    @SignUpScope
    fun provideNavigation(
        producer: NavigatorProducer,
        browserView: BrowserView,
        @Named(APP_CONTEXT)
        context: Context
    ): SignUpNavigation = SignUpNavigationImpl(producer, browserView, context)

    @Provides
    @SignUpScope
    fun provideDialogs(
        manager: DialogManager,
        resources: Resources
    ): SignUpDialogs = SignUpDialogsImpl(manager, resources)

    @Provides
    @SignUpScope
    fun provideInteractor(case: SignUpCase): SignUpInteractor = SignUpInteractorImpl(case)

    @Provides
    @SignUpScope
    fun provideSignUpCase(
        emailCase: EmailCase,
        passwordCase: PasswordCase,
        repository: SignUpRepository,
        session: SessionStore,
        errors: SignUpErrorHandler
    ): SignUpCase = SignUpCaseImpl(emailCase, passwordCase, repository, session, errors)

    @Provides
    @SignUpScope
    fun provideRepository(dao: UserDao): SignUpRepository = SignUpRepositoryImpl(dao)

    @Provides
    @SignUpScope
    fun provideErrorHandler(res: Resources): SignUpErrorHandler = SignUpErrorHandlerImpl(res)

    @Provides
    @SignUpScope
    fun provideBrowserView(): BrowserView = BrowserViewImpl()


    @Provides
    @SignUpScope
    fun provideDao(@Named(APP_CONTEXT) context: Context): UserDao {
        return LuckyDogDatabase.getDatabase(context).userDao()
    }

    @Provides
    @SignUpScope
    fun provideEmailCase(): EmailCase = EmailCaseImpl()

    @Provides
    @SignUpScope
    fun providePasswordCase(): PasswordCase = PasswordCaseImpl()
}