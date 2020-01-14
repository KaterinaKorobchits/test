package my.luckydog.di.signin

import android.content.Context
import android.content.res.Resources
import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.boundaries.signin.errors.SignInErrorHandler
import my.luckydog.boundaries.signin.repositories.SignInRepository
import my.luckydog.data.bd.LuckyDogDatabase
import my.luckydog.data.bd.UserDao
import my.luckydog.data.signin.SignInErrorHandlerImpl
import my.luckydog.data.signin.SignInRepositoryImpl
import my.luckydog.di.app.AppModule.Companion.APP_CONTEXT
import my.luckydog.domain.signin.SignInCase
import my.luckydog.domain.signin.SignInCaseImpl
import my.luckydog.domain.signin.SignInInteractorImpl
import my.luckydog.domain.validators.EmailCase
import my.luckydog.domain.validators.EmailCaseImpl
import my.luckydog.domain.validators.PasswordCase
import my.luckydog.domain.validators.PasswordCaseImpl
import my.luckydog.interactors.signin.SignInInteractor
import my.luckydog.navigation.NavigatorProducer
import my.luckydog.navigation.SignInNavigationImpl
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.fragments.signin.dialogs.SignInDialogs
import my.luckydog.presentation.fragments.signin.dialogs.SignInDialogsImpl
import my.luckydog.presentation.fragments.signin.navigation.SignInNavigation
import javax.inject.Named

@Module
class SignInModule {

    @Provides
    @SignInScope
    fun provideNavigation(producer: NavigatorProducer): SignInNavigation =
        SignInNavigationImpl(producer)

    @Provides
    @SignInScope
    fun provideDialogs(
        manager: DialogManager,
        resources: Resources
    ): SignInDialogs = SignInDialogsImpl(manager, resources)

    @Provides
    @SignInScope
    fun provideInteractor(case: SignInCase): SignInInteractor = SignInInteractorImpl(case)

    @Provides
    @SignInScope
    fun provideSignInCase(
        emailCase: EmailCase,
        passwordCase: PasswordCase,
        repository: SignInRepository,
        session: SessionStore,
        errors: SignInErrorHandler
    ): SignInCase = SignInCaseImpl(emailCase, passwordCase, repository, session, errors)

    @Provides
    @SignInScope
    fun provideRepository(dao: UserDao): SignInRepository = SignInRepositoryImpl(dao)

    @Provides
    @SignInScope
    fun provideErrorHandler(res: Resources): SignInErrorHandler = SignInErrorHandlerImpl(res)


    @Provides
    @SignInScope
    fun provideDao(@Named(APP_CONTEXT) context: Context): UserDao {
        return LuckyDogDatabase.getDatabase(context).userDao()
    }

    @Provides
    @SignInScope
    fun provideEmailCase(): EmailCase = EmailCaseImpl()

    @Provides
    @SignInScope
    fun providePasswordCase(): PasswordCase = PasswordCaseImpl()
}