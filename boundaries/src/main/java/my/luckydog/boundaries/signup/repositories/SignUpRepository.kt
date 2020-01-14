package my.luckydog.boundaries.signup.repositories

import io.reactivex.Single

interface SignUpRepository {

    fun signUp(email: String, password: String): Single<Long>
}