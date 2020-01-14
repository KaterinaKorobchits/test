package my.luckydog.domain.signup

import io.reactivex.Single
import my.luckydog.interactors.signup.SideEffect

interface SignUpCase {

    fun signUp(email: String, password: String): Single<SideEffect>
}