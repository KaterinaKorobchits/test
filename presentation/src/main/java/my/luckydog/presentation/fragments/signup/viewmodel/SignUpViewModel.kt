package my.luckydog.presentation.fragments.signup.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import my.luckydog.interactors.signup.SideEffect
import my.luckydog.interactors.signup.SideEffect.*
import my.luckydog.interactors.signup.SideEffect.Dialog.*
import my.luckydog.interactors.signup.SideEffect.Navigate.ToSignIn
import my.luckydog.interactors.signup.SideEffect.Navigate.ToTerms
import my.luckydog.interactors.signup.SideEffect.UpdateUi.*
import my.luckydog.interactors.signup.SignUpInteractor
import my.luckydog.presentation.extensions.safeGet
import kotlin.LazyThreadSafetyMode.NONE

class SignUpViewModel(
    private val interactor: SignUpInteractor
) : ViewModel(), SignUpHandler, LifecycleObserver {

    val form: SignUpForm = SignUpForm()

    val navigate: LiveData<Navigate>
        get() = navigation
    val showDialog: LiveData<Dialog>
        get() = dialogs

    private val composite by lazy(NONE) { CompositeDisposable() }

    private val navigation = MutableLiveData<Navigate>()
    private val dialogs = MutableLiveData<Dialog>()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun clear() {
        composite.clear()
    }
    init{
        Log.e("aaa", "vm up-init")
    }

    override fun onCleared() {
        Log.e("aaa", "vm up-onCleared")
        composite.dispose()
        process(Clear)
        super.onCleared()
    }

    override fun onClickSignUp() {
        val signUp = interactor.signUp(form.email.safeGet(), form.password.safeGet())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                process(HideErrors)
                process(ShowProgress)
            }
            .doOnTerminate { process(HideProgress) }
            .subscribe({ process(it) }, { process(ShowUnknownError) })
        composite.add(signUp)
    }

    override fun onClickSignIn() = process(ToSignIn(form.email.safeGet()))

    override fun onClickTermsOfUse() = process(ToTerms)

    private fun process(effect: SideEffect) = when (effect) {
        is Navigate -> navigation.value = effect
        is UpdateUi -> update(effect)
        is Dialog -> dialogs.value = effect
    }

    private fun update(update: UpdateUi) = when (update) {
        is ShowEmailError -> form.emailError.set(update.error)
        is ShowPasswordError -> form.passwordError.set(update.error)
        is ShowCredentialsError -> {
            form.emailError.set(update.email)
            form.passwordError.set(update.password)
        }
        is HideErrors -> form.hideErrors()
    }
}