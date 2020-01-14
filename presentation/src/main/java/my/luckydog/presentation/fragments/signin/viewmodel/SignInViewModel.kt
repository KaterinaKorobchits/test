package my.luckydog.presentation.fragments.signin.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import my.luckydog.interactors.signin.SideEffect
import my.luckydog.interactors.signin.SideEffect.*
import my.luckydog.interactors.signin.SideEffect.Dialog.*
import my.luckydog.interactors.signin.SideEffect.Navigate.ToSignUp
import my.luckydog.interactors.signin.SideEffect.UpdateUi.*
import my.luckydog.interactors.signin.SignInInteractor
import my.luckydog.presentation.extensions.safeGet
import kotlin.LazyThreadSafetyMode.NONE

class SignInViewModel(
    private val interactor: SignInInteractor
) : ViewModel(), SignInHandler, LifecycleObserver {

    val form: SignInForm = SignInForm()

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
        Log.e("aaa", "vm in-init")
    }

    override fun onCleared() {
        Log.e("aaa", "vm in-onCleared")
        composite.dispose()
        process(Clear)
        super.onCleared()
    }

    override fun onClickSignIn() {
        val signIn = interactor.signIn(form.email.safeGet(), form.password.safeGet())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                process(HideErrors)
                process(ShowProgress)
            }
            .doOnTerminate { process(HideProgress) }
            .subscribe({ process(it) }, { process(ShowUnknownError) })
        composite.add(signIn)
    }

    override fun onClickSignUp() {
        navigation.value = ToSignUp(form.email.safeGet())
    }

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