package my.luckydog.domain.signin

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.boundaries.signin.errors.CredentialsFormatFail
import my.luckydog.boundaries.signin.errors.EmailFormatFail
import my.luckydog.boundaries.signin.errors.PasswordFormatFail
import my.luckydog.boundaries.signin.errors.SignInError.*
import my.luckydog.boundaries.signin.errors.SignInErrorHandler
import my.luckydog.boundaries.signin.repositories.SignInRepository
import my.luckydog.domain.app.validators.EmailCase
import my.luckydog.domain.app.validators.PasswordCase
import my.luckydog.interactors.signin.SideEffect
import my.luckydog.interactors.signin.SideEffect.Dialog.ShowCredentialsIncorrect
import my.luckydog.interactors.signin.SideEffect.Dialog.ShowUnknownError
import my.luckydog.interactors.signin.SideEffect.Navigate
import my.luckydog.interactors.signin.SideEffect.UpdateUi.*
import javax.inject.Inject

class SignInCaseImpl @Inject constructor(
    private val emailCase: EmailCase,
    private val passwordCase: PasswordCase,
    private val repository: SignInRepository,
    private val session: SessionStore,
    private val errors: SignInErrorHandler
) : SignInCase {

    init {
        println("!!!init: SignInCaseImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun signIn(email: String, password: String): Single<SideEffect> {
        val validEmail = emailCase.isValid(email)

        val validPassword = passwordCase.isValid(password)

        return when {
            !validEmail && !validPassword -> Single.error(CredentialsFormatFail())
            !validEmail -> Single.error(EmailFormatFail())
            !validPassword -> Single.error(PasswordFormatFail())

            else -> repository.signIn(email, password)
                .map { session.storeUserId(it) }
                .map { Navigate.ToHome as SideEffect }
                .subscribeOn(Schedulers.io())
        }.onErrorReturn {
            when (val error = errors.process(it)) {
                is CredentialsIncorrect -> ShowCredentialsIncorrect(error.message)
                is CredentialsFormat -> ShowCredentialsError(error.emailMsg, error.passwordMsg)
                is EmailFormat -> ShowEmailError(error.message)
                is PasswordFormat -> ShowPasswordError(error.message)
                else -> ShowUnknownError
            }
        }
    }
}