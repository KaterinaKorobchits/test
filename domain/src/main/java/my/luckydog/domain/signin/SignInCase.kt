package my.luckydog.domain.signin

import io.reactivex.Single
import my.luckydog.interactors.signin.SideEffect

interface SignInCase {

    fun signIn(email: String, password: String): Single<SideEffect>
}