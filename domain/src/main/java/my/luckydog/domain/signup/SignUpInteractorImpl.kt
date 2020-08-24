package my.luckydog.domain.signup

import my.luckydog.interactors.signup.SignUpInteractor
import javax.inject.Inject

class SignUpInteractorImpl @Inject constructor(private val signUpCase: SignUpCase) :
    SignUpInteractor {

    init {
        println("!!!init: SignUpInteractorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun signUp(email: String, password: String) = signUpCase.signUp(email, password)
}