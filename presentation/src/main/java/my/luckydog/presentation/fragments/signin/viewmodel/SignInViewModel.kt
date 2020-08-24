package my.luckydog.presentation.fragments.signin.viewmodel

import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.luckydog.interactors.signin.SideEffect
import my.luckydog.interactors.signin.SideEffect.*
import my.luckydog.interactors.signin.SideEffect.Dialog.*
import my.luckydog.interactors.signin.SideEffect.Navigate.ToSignUp
import my.luckydog.interactors.signin.SideEffect.UpdateUi.*
import my.luckydog.interactors.signin.SignInInteractor
import my.luckydog.presentation.core.Event
import my.luckydog.presentation.core.extensions.safeGet
import my.luckydog.presentation.fragments.signin.dialogs.SignInDialogs
import kotlin.LazyThreadSafetyMode.NONE

class SignInViewModel(
    private val interactor: SignInInteractor,
    val dialogs: SignInDialogs
) : ViewModel(), SignInHandler, LifecycleObserver {

    /*init {
        println("A")
        viewModelScope.launch(Dispatchers.Main.immediate) { println("B")        }
        withContext() {

        }
        println("C")
    }*/

    val form: SignInForm = SignInForm()

    val navigate: LiveData<Event<Navigate>>
        get() = navigation
    val showDialog: LiveData<Event<Dialog>>
        get() = dialog

    private val composite by lazy(NONE) { CompositeDisposable() }

    private val navigation = MutableLiveData<Event<Navigate>>()
    private val dialog = MutableLiveData<Event<Dialog>>()

    fun onKeyboardChanged(status: Boolean) = form.isKeyboardShowed.set(status)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun clear() {
        composite.clear()
    }

    override fun onCleared() {
        composite.dispose()
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

    override fun onClickSignUp() = process(ToSignUp(form.email.safeGet()))

    private fun process(effect: SideEffect) = when (effect) {
        is Navigate -> navigation.value = Event(effect)
        is UpdateUi -> update(effect)
        is Dialog -> dialog.value = Event(effect)
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