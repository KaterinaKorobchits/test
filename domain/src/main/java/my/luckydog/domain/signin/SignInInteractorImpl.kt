package my.luckydog.domain.signin

import my.luckydog.interactors.signin.SignInInteractor

class SignInInteractorImpl(private val signInCase: SignInCase) : SignInInteractor {

    override fun signIn(email: String, password: String) = signInCase.signIn(email, password)
}