package my.luckydog.domain.signin

import my.luckydog.interactors.signin.SignInInteractor
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val signInCase: SignInCase) :
    SignInInteractor {

    init {
        println("!!!init: SignInInteractorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun signIn(email: String, password: String) = signInCase.signIn(email, password)
}