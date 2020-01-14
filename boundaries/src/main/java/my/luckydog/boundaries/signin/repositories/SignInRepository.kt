package my.luckydog.boundaries.signin.repositories

import io.reactivex.Single

interface SignInRepository {

    fun signIn(email: String, password: String): Single<Long>
}