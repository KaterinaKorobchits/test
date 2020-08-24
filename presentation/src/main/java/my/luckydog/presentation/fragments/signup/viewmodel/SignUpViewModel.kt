package my.luckydog.presentation.fragments.signup.viewmodel

import androidx.lifecycle.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import my.luckydog.interactors.signup.SideEffect
import my.luckydog.interactors.signup.SideEffect.*
import my.luckydog.interactors.signup.SideEffect.Dialog.*
import my.luckydog.interactors.signup.SideEffect.Navigate.ToSignIn
import my.luckydog.interactors.signup.SideEffect.Navigate.ToTerms
import my.luckydog.interactors.signup.SideEffect.UpdateUi.*
import my.luckydog.interactors.signup.SignUpInteractor
import my.luckydog.presentation.core.Event
import my.luckydog.presentation.core.extensions.safeGet
import my.luckydog.presentation.fragments.signup.dialogs.SignUpDialogs
import kotlin.LazyThreadSafetyMode.NONE

class SignUpViewModel(
    private val interactor: SignUpInteractor,
    val dialogs: SignUpDialogs
) : ViewModel(), SignUpHandler, LifecycleObserver {

    val form: SignUpForm = SignUpForm()

    val navigate: LiveData<Event<Navigate>>
        get() = navigation
    val showDialog: LiveData<Event<Dialog>>
        get() = dialog

    private val composite by lazy(NONE) { CompositeDisposable() }

    private val navigation = MutableLiveData<Event<Navigate>>()
    private val dialog = MutableLiveData<Event<Dialog>>()

    fun onKeyboardChanged(status: Boolean) = form.isKeyboardShowed.set(status)

    fun load(single: Single<Unit>){
        val load = single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        composite.add(load)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun clear() {
        composite.clear()
    }

    override fun onCleared() {
        composite.dispose()
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