package my.luckydog.interactors.signup

import io.reactivex.Single

interface SignUpInteractor {

    fun signUp(email: String, password: String): Single<SideEffect>
}