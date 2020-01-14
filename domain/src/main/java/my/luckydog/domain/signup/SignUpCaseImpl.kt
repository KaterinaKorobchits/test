package my.luckydog.domain.signup

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.boundaries.signup.errors.CredentialsFormatFail
import my.luckydog.boundaries.signup.errors.EmailFormatFail
import my.luckydog.boundaries.signup.errors.PasswordFormatFail
import my.luckydog.boundaries.signup.errors.SignUpError.*
import my.luckydog.boundaries.signup.errors.SignUpErrorHandler
import my.luckydog.boundaries.signup.repositories.SignUpRepository
import my.luckydog.domain.validators.EmailCase
import my.luckydog.domain.validators.PasswordCase
import my.luckydog.interactors.signup.SideEffect
import my.luckydog.interactors.signup.SideEffect.Dialog.ShowUnknownError
import my.luckydog.interactors.signup.SideEffect.Navigate
import my.luckydog.interactors.signup.SideEffect.UpdateUi.*

class SignUpCaseImpl(
    private val emailCase: EmailCase,
    private val passwordCase: PasswordCase,
    private val repository: SignUpRepository,
    private val session: SessionStore,
    private val errors: SignUpErrorHandler
) : SignUpCase {

    override fun signUp(email: String, password: String): Single<SideEffect> {
        val validEmail = emailCase.isValid(email)
        val validPassword = passwordCase.isValid(password)

        return when {
            !validEmail && !validPassword -> Single.error(CredentialsFormatFail())
            !validEmail -> Single.error(EmailFormatFail())
            !validPassword -> Single.error(PasswordFormatFail())

            else -> repository.signUp(email, password)
                .map { session.storeUserId(it) }
                .map { Navigate.ToHome as SideEffect }
                .subscribeOn(Schedulers.io())
        }.onErrorReturn {
            when (val error = errors.process(it)) {
                is EmailExist -> ShowEmailError(error.message)
                is EmailFormat -> ShowEmailError(error.message)
                is PasswordFormat -> ShowPasswordError(error.message)
                is CredentialsFormat -> ShowCredentialsError(error.emailMsg, error.passwordMsg)
                else -> ShowUnknownError
            }
        }
    }
}