package my.luckydog.domain.signup

import my.luckydog.interactors.signup.SignUpInteractor

class SignUpInteractorImpl(private val signUpCase: SignUpCase) : SignUpInteractor {

    override fun signUp(email: String, password: String) = signUpCase.signUp(email, password)
}