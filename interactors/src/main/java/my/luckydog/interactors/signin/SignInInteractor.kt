package my.luckydog.interactors.signin

import io.reactivex.Single

interface SignInInteractor {

    fun signIn(email: String, password: String): Single<SideEffect>
}